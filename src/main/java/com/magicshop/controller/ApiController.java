package com.magicshop.controller;
import com.magicshop.service.ShopService;import jakarta.servlet.http.HttpSession;import org.springframework.web.bind.annotation.*;import java.math.BigDecimal;import java.util.Map;
@RestController @RequestMapping("/api") public class ApiController{
 private final ShopService s;public ApiController(ShopService s){this.s=s;} private Long uid(HttpSession x){return (Long)x.getAttribute("uid");}
 @PostMapping("/register") public Object reg(@RequestBody Map<String,String> b,HttpSession x){var u=s.register(b.get("username"),b.get("email"),b.get("password"),b.get("displayName"),b.get("phone"));x.setAttribute("uid",u.id());return u;}
 @PostMapping("/login") public Object login(@RequestBody Map<String,String>b,HttpSession x){var u=s.login(b.get("login"),b.get("password"));x.setAttribute("uid",u.id());return u;}
 @PostMapping("/logout") public Object logout(HttpSession x){x.invalidate();return Map.of("message","ok");}
 @GetMapping("/me") public Object me(HttpSession x){return s.current(uid(x));}
 @GetMapping("/products") public Object products(HttpSession x){s.current(uid(x));return s.products();}
 @PostMapping("/cart/items") public Object add(@RequestBody Map<String,Object>b,HttpSession x){s.addCart(uid(x),((Number)b.get("productId")).longValue(),((Number)b.getOrDefault("quantity",1)).intValue());return Map.of("message","added");}
 @PostMapping("/orders") public Object order(@RequestBody Map<String,String>b,HttpSession x){return s.createOrder(uid(x),b.get("deliveryAddress"),b.get("paymentMethod"));}
 @GetMapping("/orders") public Object orders(HttpSession x){return s.orders(uid(x));}
 @GetMapping("/admin/orders") public Object all(HttpSession x){s.admin(uid(x));return s.allOrders();}
 @PostMapping("/admin/products") public Object pc(@RequestBody Map<String,Object>b,HttpSession x){s.productCreate(uid(x),(String)b.get("name"),(String)b.get("description"),new BigDecimal(b.get("price").toString()),Integer.parseInt(b.get("quantity").toString()));return Map.of("message","created");}
 @PutMapping("/admin/products/{id}") public Object pu(@PathVariable long id,@RequestBody Map<String,Object>b,HttpSession x){s.productUpdate(uid(x),id,(String)b.get("name"),(String)b.get("description"),new BigDecimal(b.get("price").toString()),Integer.parseInt(b.get("quantity").toString()));return Map.of("message","updated");}
 @DeleteMapping("/admin/products/{id}") public Object pd(@PathVariable long id,HttpSession x){s.productDelete(uid(x),id);return Map.of("message","deleted");}
 @PatchMapping("/admin/orders/{id}/status") public Object st(@PathVariable long id,@RequestBody Map<String,String>b,HttpSession x){s.status(uid(x),id,b.get("status"));return Map.of("message","updated");}
}
