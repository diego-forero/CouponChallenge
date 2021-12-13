# Coupon Challenge:

API for the MELI Coupon Challenge.

To run the API download the API branch of repository and run the application with the command:
./mvnw spring-boot:run

After that the application will be running on port 8080. Then you can make a POST request to the "coupon" service: http://localhost:8080/coupon.

The application has the sample items database of the challenge (five items) and it doesn't make the GET request to get a MELI item price, then if you want to modify the items database it is necessary to change that "internal database" items. To do that you should go to "src\main\java\com\meli\couponchallenge\CouponItemsResource.java" and change the items (meliItems variable) in the CouponItemsResource public class. This items must be a Map<String, Float> object as the follows:


    Map<String, Float> meliItems  = new HashMap<String, Float>() {{
        put("MLA1", 100f);
        put("MLA2", 210f);
        put("MLA3", 260f);
        put("MLA4", 80f);
        put("MLA5", 90f);
    }};

Thanks,
    