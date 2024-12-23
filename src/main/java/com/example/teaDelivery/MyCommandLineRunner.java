package com.example.teaDelivery;

import com.example.teaDelivery.models.entity.*;
import com.example.teaDelivery.models.enums.OrderStatus;
import com.example.teaDelivery.models.enums.UserRoles;
import com.example.teaDelivery.repository.*;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
    private final UserDiscountRepository userDiscountRepository;
    private final DiscountRepository discountRepository;
    private final TeaInOrderRepository teaInOrderRepository;
    private final TeaOrderRepository teaOrderRepository;
    private final UserRepository userRepository;
    private final TeaRepository teaRepository;
    private final IngredientRepository ingredientRepository;
    private final TeaIngredientRepository teaIngredientRepository;
    private final SupplierRepository supplierRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    private final String defaultPassword;


    public MyCommandLineRunner(UserDiscountRepository userDiscountRepository,
                               DiscountRepository discountRepository,
                               TeaInOrderRepository teaInOrderRepository, TeaOrderRepository teaOrderRepository,
                               UserRepository userRepository, TeaRepository teaRepository,
                               IngredientRepository ingredientRepository,
                               TeaIngredientRepository teaIngredientRepository, SupplierRepository supplierRepository,
                               PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository,
                               @Value("${app.default.password}") String defaultPassword) {
        this.userDiscountRepository = userDiscountRepository;
        this.discountRepository = discountRepository;
        this.teaInOrderRepository = teaInOrderRepository;
        this.teaOrderRepository = teaOrderRepository;
        this.userRepository = userRepository;
        this.teaRepository = teaRepository;
        this.ingredientRepository = ingredientRepository;
        this.teaIngredientRepository = teaIngredientRepository;
        this.supplierRepository = supplierRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        int teaQ = 100;
        int ingredientsQ = 4;

        Faker faker = new Faker();
        Random random = new Random();

        String blackTea = "black tea";
        String greenTea = "green tea";

        //Roles
        Role newUserRole = new Role(UserRoles.USER);
        Role newAdminRole = new Role(UserRoles.ADMIN);
        userRoleRepository.save(newUserRole);
        userRoleRepository.save(newAdminRole);

        //User
        Role userRole = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();
        Role adminRole = userRoleRepository.
                findRoleByName(UserRoles.ADMIN).orElseThrow();

        User userA = new User("Admin",passwordEncoder.encode(defaultPassword),"admin@gmail.com",
                "Admin Adminovich", LocalDate.now(),"89000000000",0);
        userA.setRoles(List.of(adminRole));
        User userU = new User("User",passwordEncoder.encode(defaultPassword),"user@gmail.com",
                "User Normalnovich", LocalDate.now(),"89432101234",0);
        userU.setRoles(List.of(userRole));
        User userG = new User("UserG",passwordEncoder.encode(defaultPassword),"sky@gmail.com",
                "User God", LocalDate.of(1,1,1),"86666666666",1000);
        userG.setRoles(List.of(userRole));
        userRepository.save(userA);
        userRepository.save(userU);
        userRepository.save(userG);

        //Discounts
        Discount discountG = new Discount("Green friday","Скидки на весь зеленый чай", greenTea,0.1,100);
        Discount discountB = new Discount("Black friday","Скидки на весь черный чай", blackTea,0.1,100);

        discountRepository.save(discountG);
        discountRepository.save(discountB);

        //DiscountUser
            //4 скидки для пользователя User God
        for (int i = 0; i < 2; i++) {
            userDiscountRepository.save(new UserDiscount(discountB, userG, false));
            userDiscountRepository.save(new UserDiscount(discountG, userG, false));
        }
        UserDiscount userDiscount1F = new UserDiscount(discountB, userG, true); //Были использованы
        UserDiscount userDiscount2F = new UserDiscount(discountG, userG, true); //Были использованы
        userDiscountRepository.save(userDiscount1F);
        userDiscountRepository.save(userDiscount2F);

        //Supplier
        Supplier supplier1 = new Supplier();
        supplier1.setId(1L);
        supplier1.setSupplier_name("ООО \"Джей эф кей\"");
        supplier1.setEmail("ooogfk@gmail.com");
        supplierRepository.save(supplier1);

        //Ingredient
        Ingredient[] ingredients = new Ingredient[7];
        ingredients[0] = new Ingredient("Яблоко");
        ingredients[1] = new Ingredient("Апельсин");
        ingredients[2] = new Ingredient("Мандарин");
        ingredients[3] = new Ingredient("Лесные ягоды");
        ingredients[4] = new Ingredient("Шоколадная стружка");
        ingredients[5] = new Ingredient("Листья чая");
        ingredients[6] = new Ingredient("Чайные листья");
        for (int i = 0;i < ingredients.length;i++){
            ingredientRepository.save(ingredients[i]);
        }

        //Tea
        Tea tea1 = new Tea();
        tea1.setSort("black tea");
        tea1.setName("Апельсиновое печенье");
        tea1.setImage("orange-cookie.webp");
        tea1.setCost(200);
        tea1.setDescription("вкусный чай");
        tea1.setSuppliers(supplier1);
        tea1.setAvailability(true);
        teaRepository.save(tea1);
        for (int i = 0; i < teaQ; i++) {
            Tea newTea = new Tea();
            newTea.setSort(i % 2 == 0 ? blackTea : greenTea);
            newTea.setName(faker.gameOfThrones().character());
            newTea.setImage(i + ".webp");
            newTea.setCost(random.nextInt(150,200));
            newTea.setDescription(faker.gameOfThrones().quote());
            newTea.setSuppliers(supplier1);
            newTea.setAvailability(true);
            teaRepository.save(newTea);
            for (int j = 0; j < ingredientsQ;j++){
                TeaIngredient teaIngredient = new TeaIngredient();
                teaIngredient.setIngredientId(ingredients[random.nextInt(0,ingredients.length)]);
                teaIngredient.setTeaId(newTea);
                teaIngredientRepository.save(teaIngredient);
//                Ingredient ingredient = new Ingredient();
//                ingredient.setIngredient_name(faker.cat().breed());
//                ingredientRepository.save(ingredient);
//                TeaIngredient teaIngredient = new TeaIngredient();
//                teaIngredient.setIngredientId(ingredient);
//                teaIngredient.setTeaId(newTea);
//                teaIngredientRepository.save(teaIngredient);
            }
        }

        //TeaOrder
        TeaOrder teaOrder1 = new TeaOrder(
                userG, LocalDateTime.of(2024,1,1,12,13),
                discountB, tea1.getCost(),LocalDateTime.now(),
                "sky","102240","haven","321", OrderStatus.ORDERED);

        TeaOrder teaOrder2 = new TeaOrder(
                userG, LocalDateTime.of(2024,2,2,23,24),
                discountB, tea1.getCost(),LocalDateTime.now(),
                "sky","102240","haven","321", OrderStatus.PACKED);
//        TeaOrder teaOrder3 = new TeaOrder(
//                userG, LocalDateTime.of(2024,2,2,23,24),
//                personalDiscountB, tea1.getCost(),LocalDateTime.now(),
//                "sky","102240","haven","321", OrderStatus.TRANSIT);
//
//        TeaOrder teaOrder4 = new TeaOrder(
//                userG, LocalDateTime.of(2024,2,2,23,24),
//                personalDiscountB, tea1.getCost(),LocalDateTime.now(),
//                "sky","102240","haven","321", OrderStatus.DELIVERED);
//        TeaOrder teaOrder5 = new TeaOrder(
//                userG, LocalDateTime.of(2024,5,1,12,13),
//                personalDiscountB, tea1.getCost(),LocalDateTime.now(),
//                "sky","102240","haven","321", OrderStatus.ORDERED);
        teaOrderRepository.save(teaOrder1);
        teaOrderRepository.save(teaOrder2);
//        teaOrderRepository.save(teaOrder3);
//        teaOrderRepository.save(teaOrder4);
//        teaOrderRepository.save(teaOrder5);

        //TeaInOrder
        TeaInOrder teaInOrder1 = new TeaInOrder(teaOrder1,tea1);
        TeaInOrder teaInOrder2 = new TeaInOrder(teaOrder2,tea1);
//        TeaInOrder teaInOrder3 = new TeaInOrder(teaOrder3,tea1);
//        TeaInOrder teaInOrder4 = new TeaInOrder(teaOrder4,tea1);
//        TeaInOrder teaInOrder5 = new TeaInOrder(teaOrder5,tea1);

        teaInOrderRepository.save(teaInOrder1);
        teaInOrderRepository.save(teaInOrder2);
//        teaInOrderRepository.save(teaInOrder3);
//        teaInOrderRepository.save(teaInOrder4);
//        teaInOrderRepository.save(teaInOrder5);


        //Basket
//        List<BasketItemOld> basketItems = new ArrayList<>();
//        BasketItemOld item = new BasketItemOld(tea1.getId(),tea1.getName(),tea1.getCost(),0,tea1.getCost());
//        basketItems.add(item);
//        basketItems.add(item);
//        basketItems.add(item);
//        BasketOld basket = new BasketOld(userU.getId().toString(),-1,-1,basketItems);
//        basketRepository.saveBasket(basket.getUserId(),basket);




        System.out.println("start");
    }
}
