/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.web;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import wizut.tpsi.ogloszenia.jpa.BodyStyle;
import wizut.tpsi.ogloszenia.jpa.CarManufacturer;
import wizut.tpsi.ogloszenia.jpa.CarModel;
import wizut.tpsi.ogloszenia.jpa.FuelType;
import wizut.tpsi.ogloszenia.jpa.Offer;
import wizut.tpsi.ogloszenia.services.OffersService;

/**
 *
 * @author Czaro
 */
@Controller
public class HomeController {
    
    @Autowired
    private OffersService offersService;
    
 //   @RequestMapping("/")
   // public String home(Model model) {
   //     model.addAttribute("manufacturer", offersService.getCarManufacturer(2));
   //     model.addAttribute("model", offersService.getModel(3));
   //     return "home";
   // }
    
    @GetMapping("/")
    public String home(Model model, OfferFilter offerFilter) {
        
//        List<CarManufacturer> carManufacturers = offersService.getCarManufacturers();
//
//        model.addAttribute("carManufacturers", carManufacturers);
//        
//        List<CarModel> carModel = offersService.getCarModels();
//        model.addAttribute("carModel", carModel);
//        
//        List<BodyStyle> bodyStyle = offersService.getBodyStyles();
//        
//        model.addAttribute("bodyStyle", bodyStyle);
//         
//        List<FuelType> fuelType = offersService.getFuelTypes();
//        model.addAttribute("fuelType", fuelType);
//        
//        List<CarModel> carModel1 = offersService.getCarModels(5);
//        model.addAttribute("carModel1", carModel1);
//        
//        List<Offer> offer = offersService.getOffers();
//        model.addAttribute("offer", offer);
//        
//        List<Offer> offer2 = offersService.getOffersByModel(10);
//        model.addAttribute("offer2", offer2);
//        
//        List<CarModel> offer3 = offersService.getOffersByManufacturer(3);
//        model.addAttribute("offer3", offer3);
//        
//        List<Offer> offer4 = offersService.getOffer(1);
//        model.addAttribute("offer4", offer4);

        List<CarManufacturer> carManufacturers = offersService.getCarManufacturers();
        List<CarModel> carModels = offersService.getCarModels();

       List<Offer> offers;

       if(offerFilter.getManufacturerId()!=null) {
           offers = offersService.getOffersByManufacturer(offerFilter.getManufacturerId());
           List<CarModel> filteredCarModels = offersService.getCarModelsByManufacturer(offerFilter.getManufacturerId());
           model.addAttribute("filteredCarModels", filteredCarModels);
       } else {
           offers = offersService.getOffers();
       }

         model.addAttribute("carManufacturers", carManufacturers);
         model.addAttribute("carModels", carModels);
         model.addAttribute("offers", offers);
       
        return "offersList";
        
       
    }
    
    @GetMapping("/offer/{id}")
    public String offerDetails(Model model, @PathVariable("id") Integer id) {
        Offer offer = offersService.getOffer(id);
        model.addAttribute("offer", offer);
        return "offerDetails";
    }
    
    @GetMapping("/newoffer")
    public String newOfferForm(Model model, Offer offer) {
        List<CarModel> carModels = offersService.getCarModels();
        List<BodyStyle> bodyStyles = offersService.getBodyStyles();
        List<FuelType> fuelTypes = offersService.getFuelTypes();

        model.addAttribute("carModels", carModels);
        model.addAttribute("bodyStyles", bodyStyles);
        model.addAttribute("fuelTypes", fuelTypes);
        model.addAttribute("header", "Nowe ogłoszenie");
        model.addAttribute("action", "/newoffer");
        
        return "offerForm";
    }
    
    @PostMapping("/newoffer")
    public String saveNewOffer(Model model, @Valid Offer offer, BindingResult binding) {
        if(binding.hasErrors()) {
            List<CarModel> carModels = offersService.getCarModels();
            List<BodyStyle> bodyStyles = offersService.getBodyStyles();
            List<FuelType> fuelTypes = offersService.getFuelTypes();

            model.addAttribute("carModels", carModels);
            model.addAttribute("bodyStyles", bodyStyles);
            model.addAttribute("fuelTypes", fuelTypes);
            model.addAttribute("header", "Nowe ogłoszenie");
            model.addAttribute("action", "/newoffer");

            return "offerForm";
        }
        offer = offersService.createOffer(offer);

        return "redirect:/offer/" + offer.getId();
    }   
    
    @RequestMapping("/deleteoffer/{id}")
    public String deleteOffer(Model model, @PathVariable("id") Integer id) {
        Offer offer = offersService.deleteOffer(id);

        model.addAttribute("offer", offer);    
        return "deleteOffer";
    }
    
    @GetMapping("/editoffer/{id}")
    public String editOffer(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("header", "Edycja ogłoszenia");
        model.addAttribute("action", "/editoffer/" + id);
        
        Offer offer = offersService.getOffer(id);
        model.addAttribute("offer", offer);
        
        List<CarModel> carModels = offersService.getCarModels();
        List<BodyStyle> bodyStyles = offersService.getBodyStyles();
        List<FuelType> fuelTypes = offersService.getFuelTypes();

        model.addAttribute("carModels", carModels);
        model.addAttribute("bodyStyles", bodyStyles);
        model.addAttribute("fuelTypes", fuelTypes);
        return "offerForm";
    }
    
    @PostMapping("/editoffer/{id}")
    public String saveEditedOffer(Model model, @PathVariable("id") Integer id, @Valid Offer offer, BindingResult binding) {
        if(binding.hasErrors()) {
            model.addAttribute("header", "Edycja ogłoszenia");
            model.addAttribute("action", "/editoffer/" + id);

            List<CarModel> carModels = offersService.getCarModels();
            List<BodyStyle> bodyStyles = offersService.getBodyStyles();
            List<FuelType> fuelTypes = offersService.getFuelTypes();

            model.addAttribute("carModels", carModels);
            model.addAttribute("bodyStyles", bodyStyles);
            model.addAttribute("fuelTypes", fuelTypes);

            return "offerForm";
          }

        offersService.saveOffer(offer);

        return "redirect:/offer/" + offer.getId();
    }
}
