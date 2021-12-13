package com.meli.couponchallenge;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import com.meli.models.CouponItems;
import static com.meli.services.CouponService.calculate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 *
 * @author Diego Forero
 */

@RestController
public class CouponItemsResource {

    Map<String, Float> meliItems  = new HashMap<String, Float>() {{
        put("MLA1", 100f);
        put("MLA2", 210f);
        put("MLA3", 260f);
        put("MLA4", 80f);
        put("MLA5", 90f);
    }};
    

    @PostMapping(path="coupon", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces ={MediaType.APPLICATION_JSON_VALUE})
//    public Map<String, Float> getCouponItems(@RequestBody CouponItems data) {  *** Base de código solicitada. No se usa porque se observa que no es la más óptima
    public CouponItems getCouponItems(@RequestBody CouponItems data) {
        List<String> userItemsIds = data.getItem_ids();
        Map<String, Float> userItems=userItemsIds.stream()
                .filter(meliItems::containsKey)
                .collect(Collectors.toMap(Function.identity(), meliItems::get));
        CouponItems result =new CouponItems();
        if (calculate(userItems,data.getAmount()).getItem_ids().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        result.setAmount(calculate(userItems,data.getAmount()).getAmount());
        result.setItem_ids(calculate(userItems,data.getAmount()).getItem_ids());
        return result;
    }    
}
