package meli;

import static CouponService.CouponService.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import models.CouponItems;

/**
 *
 * @author Diego Forero
 */
@Path("Coupon")
public class CouponItemsResource {

    Map<String, Float> meliItems  = new HashMap<String, Float>() {{
        put("MLA1", 100f);
        put("MLA2", 210f);
        put("MLA3", 260f);
        put("MLA4", 80f);
        put("MLA5", 90f);
    }};
    
    
    @POST
    @Path("coupon")
    @Produces("application/json")
    @Consumes("application/json")
//    public Map<String, Float> getCouponItems(CouponItems data) {
    public CouponItems getCouponItems(CouponItems data) throws IOException {
        List<String> userItemsIds = data.getItem_ids();
        Map<String, Float> userItems=userItemsIds.stream()
                .filter(meliItems::containsKey)
                .collect(Collectors.toMap(Function.identity(), meliItems::get));
        CouponItems result =new CouponItems();
        if (calculate(userItems,data.getAmount()).getItem_ids().isEmpty()){
            throw new IOException("Not found item with the minimum value");
        }
        result.setAmount(calculate(userItems,data.getAmount()).getAmount());
        result.setItem_ids(calculate(userItems,data.getAmount()).getItem_ids());

        return result;
    }    
}
