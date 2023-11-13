package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.bookStore.entity.Book;
import com.bookStore.entity.Cart;
import com.bookStore.entity.Order;
import com.bookStore.entity.OrderBook;
import com.bookStore.entity.User;
import com.bookStore.service.BookService;
import com.bookStore.service.CartService;
import com.bookStore.service.OrderService;
import com.bookStore.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;




@Controller
public class Appcontroller {
	@Autowired
    private BookService bookservice;
	
	@Autowired
    private CartService cartService; 
	
	@Autowired
    private OrderService orderService;
	
    @Autowired
    private UserService userService;

    @RequestMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookservice.deleteById(id);
        return "redirect:/editbook";
    }
    
    @GetMapping("/removeFromCart/{book_id}")
    public String removeFromCart(@PathVariable int book_id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Cart cart = cartService.getCartByUser(user);
            if (cart != null) {
                // Get the list of books in the cart
                List<Book> books = cart.getBooks();
                
                // Find and remove the book with the specified book_id
                books.removeIf(book -> book.getId() == book_id);
                
                // Save the updated cart
                cartService.saveCart(cart);
            }
        }
        return "redirect:/cart";
    }


    @PostMapping("/checkout")
    public String checkout(@RequestParam("quantity") int quantity, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Get the user's cart
            Cart cart = cartService.getCartByUser(user);

            if (cart != null && !cart.getBooks().isEmpty()) {
                // Create an order for each book in the cart
                for (Book book : cart.getBooks()) {
                    Order order = new Order();
                    order.setUser(user);
                    order.setOrder_date(cart.getAdditionDate()); 
                    order.setOrderedBook(book);
                    order.setQuantity(quantity);  // Set the selected quantity

                    double bookPrice = Double.parseDouble(book.getPrice());
                    double total_Price = bookPrice * quantity;
                    order.setTotal_Price(total_Price);
                    // Set the cart_id attribute
                    order.setCart(cart);

                    orderService.saveOrder(order);
                }

                // Clear the user's cart after checkout
                cartService.clearCart(cart);
            }
        }

        // Redirect to a confirmation page or home page
        return "redirect:/order";
    }



    
    
    @GetMapping("/order")
    public String showOrderHistory(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            // Fetch orders for the logged-in user
            List<Order> orders = orderService.getOrdersByUser(user);

            // Add orders to the model
            model.addAttribute("orders", orders);
        }

        return "order";
    }

    @GetMapping("/orderDetails/{orderId}")
    public String orderDetails(@PathVariable Long orderId, Model model) {
   
        Optional<Order> orderOptional = orderService.getOrderById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            
            model.addAttribute("order", order);

            // Assuming your orderDetails page is named "orderDetails.html"
            return "orderDetails";
        } else {
            // If order is not found, you might want to handle this scenario
            // Redirect to an error page or display an error message
            model.addAttribute("errorMessage", "Order not found");
            // You should create an error.html template for this
            return "error";
        }
    }

    
    
    @RequestMapping("/deleteuser/{id}")
    public String deleteuser(@PathVariable("id") int id) {
    	userService.deleteById(id);
        return "redirect:/edituser";
    }
    

    @PostMapping("/addToCart/{bookId}")
    public String addToCart(@PathVariable int bookId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            // Redirect to the login page if the user is not logged in
            return "redirect:/login";
        }

        Book book = bookservice.getBookById(bookId);

        if (book != null) {
            // Create a new Cart instance
            Cart cart = cartService.getCartByUser(user);
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
            }

            // Check if the book is not already in the cart, add it if it's not.
            if (!cart.getBooks().contains(book)) {
                cart.addBook(book);

                // Set the current date to the cart's additionDate attribute
                cart.setAdditionDate(LocalDateTime.now());

                // Save the updated cart
                cartService.saveCart(cart);
            }
        }

        return "redirect:/bookstore";
    }





    
    @GetMapping("/bookstore") 
    public ModelAndView getAllBooks() {
    	List<Book>list=bookservice.getAllBooks();	
    	return new ModelAndView("bookstore","book",list);
    }
    
    @PostMapping("/bookstore")
    public String login(
        @RequestParam String username, 
        @RequestParam String password,
        Model model,
        HttpSession session
    ) {
        User user = userService.loginUser(username, password);

        if (user != null) {
            String role = user.getRole(); 
            // Store the user in the session
            session.setAttribute("user", user);

            if ("admin".equals(role)) {
                return "redirect:/adminhome";
            } else {
                return "redirect:/bookstore";
            }
        } else {
            // Set the "loginError" attribute to true
            model.addAttribute("loginError", true);
            return "login";
        }
    }

  

    
    @GetMapping("/")
    public String home() {
        return "home";
    }

   
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Cart cart = cartService.getCartByUser(user);
            model.addAttribute("cart", cart);
        }
        return "cart";
    }


    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); 
        return "register";
    }
    
    @GetMapping("/addbook")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "addbook";
    }
    
    @GetMapping("/adminhome")
    public String adminhome() {
        return "adminhome";
    }
    
    @GetMapping("/editbook")
    public ModelAndView editbook() {
    	List<Book>list=bookservice.getAllBooks();	
    	return new ModelAndView("editbook","book",list);
    }
    
    @GetMapping("/edituser") 
    public ModelAndView edituser() {
    	List<User>list=userService.getAlluser();	
    	return new ModelAndView("edituser","users",list);
    }
    
    
    @GetMapping("/allbook") 
    public ModelAndView getAllBook() {
    	List<Book>list=bookservice.getAllBooks();	
    	return new ModelAndView("allbook","book",list);
    }
    
    @GetMapping("/alluser") 
    public ModelAndView getAlluser() {
    	List<User>list=userService.getAlluser();	
    	return new ModelAndView("alluser","users",list);
    }
  
    
    
    
    @GetMapping("/adduser")
    public String adduser(Model model) {
    	model.addAttribute("user", new User());
        return "adduser";
    }
    
    
    @PostMapping("/adduser")
    public String adduser(
        @ModelAttribute User user,
        Model model
    ) {
            userService.registerNewUser(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPhoneno(), user.getPassword(),user.getRole());
            model.addAttribute("firstname", user.getFirstname());
            model.addAttribute("lastname", user.getLastname());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("phoneno", user.getPhoneno());
            model.addAttribute("password", user.getPassword());
            model.addAttribute("Role", user.getRole());
            return "redirect:/adminhome";
      }
    
    @PostMapping("/addbook")
    public String addbook(@ModelAttribute Book book, @RequestParam("cover") MultipartFile cover, Model model) {
        if (book.getTitle().isEmpty() || book.getGenre().isEmpty() || book.getAuthor().isEmpty() ||  book.getQuantity() == 0  || book.getPrice().isEmpty()) {
            // Handle validation errors
            // ...

            // Return the view where you want to display the error messages
            return "addbook";
        } else {
            if (cover != null && !cover.isEmpty()) {
                try {
                	byte[] coverBytes = cover.getBytes();
                	System.out.println("Cover image size: " + coverBytes.length);
                	book.setCover(coverBytes);

                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception
                    // You might want to set an error attribute in the model and return the view
                    model.addAttribute("uploadError", "Error uploading the cover image.");
                    return "addbook";
                }
            }
            
            // Call the service method to save the book
            bookservice.registerNewBook(book.getTitle(), book.getAuthor(), book.getGenre(), book.getPrice(),book.getQuantity(), cover);
            
            // Redirect to a different URL or view after successful submission
            return "redirect:/adminhome";
        }
    }

    
    



    
    
    
    @PostMapping("/register")
    public String register(
        @ModelAttribute User user,
        Model model
    ) {
        if (user.getFirstname().isEmpty() || user.getLastname().isEmpty() || user.getEmail().isEmpty() || user.getPhoneno().isEmpty() || user.getPassword().isEmpty()) {
            model.addAttribute("registererror","Please Fill All The Data.");

            if (user.getFirstname().isEmpty()) {
                model.addAttribute("firstnameerror", "Please provide First Name.");
            }
            if (user.getLastname().isEmpty()) {
                model.addAttribute("lastnameerror", "Please provide Last Name.");
            }
            if (user.getEmail().isEmpty()) {
                model.addAttribute("emailerror", "Please provide Email.");
            }
            if (user.getPhoneno().isEmpty()) {
                model.addAttribute("phonenoerror","Please provide Phone No.");
            }
            if (user.getPassword().isEmpty()) {
                model.addAttribute("passworderror", "Please provide Password.");
            }
            return "register";
        } else {
            userService.registerNewUser(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPhoneno(), user.getPassword(),user.getRole());
            model.addAttribute("firstname", user.getFirstname());
            model.addAttribute("lastname", user.getLastname());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("phoneno", user.getPhoneno());
            model.addAttribute("password", user.getPassword());
            model.addAttribute("Role", user.getRole());
            return "redirect:/login";
        }
    }
    
    
 
    

}