package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import domain.Antenna;
import domain.CreditCard;
import domain.Handyworker;
import domain.MaintenanceRequest;
import domain.User;
import exceptions.CreditCardExpiredException;
import services.HandyworkerService;
import services.MaintenanceRequestService;
import services.UserService;

@Controller
@RequestMapping("/maintenanceRequests/user")
public class MaintenanceRequestUserController extends AbstractController {

    @Autowired
    private MaintenanceRequestService maintenanceRequestService;
    @Autowired
    private UserService userService;
    @Autowired
    private HandyworkerService handyworkerService;


    public MaintenanceRequestUserController()
    {
        super();
    }

    @RequestMapping("/listNotServiced")
    public ModelAndView listNotServiced()
    {
        ModelAndView result;
        final User user = this.userService.findPrincipal();
        final Collection<MaintenanceRequest> maintenanceRequests = this.userService.findNotServedMainteinanceRequest(user);

        result = new ModelAndView("maintenanceRequests/list");
        result.addObject("requestURI", "maintenanceRequests/user/listNotServiced.do");
        result.addObject("maintenanceRequests", maintenanceRequests);

        return result;
    }

    @RequestMapping("/listServiced")
    public ModelAndView listServiced()
    {
        ModelAndView result;
        final User user = this.userService.findPrincipal();

        final Collection<MaintenanceRequest> maintenanceRequests = this.userService.findServedMainteinanceRequest(user);
        final boolean done = true;

        result = new ModelAndView("maintenanceRequests/list");
        result.addObject("requestURI", "maintenanceRequests/user/listServiced.do");
        result.addObject("maintenanceRequests", maintenanceRequests);
        result.addObject("done", done);

        return result;
    }

    @RequestMapping("/create")
    public ModelAndView create(@CookieValue(required = false, value = "creditCard") final String creditCardCookieString)
    {
        ModelAndView result;
        MaintenanceRequest maintenanceRequest;

        maintenanceRequest = this.maintenanceRequestService.create();
        if (creditCardCookieString != null) {
            maintenanceRequest.setCreditCard(CreditCard.fromCookieString(creditCardCookieString));
        }
        result = this.createEditModelAndView(maintenanceRequest);

        return result;
    }

    protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest)
    {
        ModelAndView result;

        result = this.createEditModelAndView(maintenanceRequest, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final MaintenanceRequest maintenanceRequest, final String message)
    {
        ModelAndView result;
        final User u = this.userService.findPrincipal();
        final Collection<Handyworker> handyworkers = this.handyworkerService.findAll();
        final Collection<Antenna> antennas = u.getAntennas();

        result = new ModelAndView("maintenanceRequests/create");
        result.addObject("maintenanceRequest", maintenanceRequest);
        result.addObject("handyworkers", handyworkers);
        result.addObject("antennas", antennas);

        if (message != null && !message.isEmpty()) {
            result.addObject("globalErrorMessage", message);
        }

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final MaintenanceRequest maintenanceRequest, final BindingResult binding, final HttpServletResponse response)
    {
        ModelAndView result;

        if (binding.hasErrors()) {
            System.out.println(binding.getAllErrors());
            result = this.createEditModelAndView(maintenanceRequest);
        } else {
            try {
                this.maintenanceRequestService.save(maintenanceRequest);
                result = new ModelAndView("redirect:listNotServiced.do");
                Cookie cookie = new Cookie("creditCard", CreditCard.toCookieString(maintenanceRequest.getCreditCard()));
                cookie.setPath("/");
                response.addCookie(cookie);
            } catch (CreditCardExpiredException ex) {
                binding.rejectValue("creditCard.expirationMonth", "credit_cards.error.expired");
                binding.rejectValue("creditCard.expirationYear", "credit_cards.error.expired");
                result = this.createEditModelAndView(maintenanceRequest);
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(maintenanceRequest, "misc.commit.error");
            }
        }

        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(final MaintenanceRequest maintenanceRequest, final BindingResult binding)
    {
        ModelAndView result;

        try {
            this.maintenanceRequestService.delete(maintenanceRequest);
            result = new ModelAndView("redirect:list.do");
        } catch (final Throwable oops) {
            result = this.createEditModelAndView(maintenanceRequest, "misc.commit.error");
        }

        return result;
    }
}
