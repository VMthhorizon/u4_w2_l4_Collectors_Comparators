package vincenzomola;

import vincenzomola.entities.Customer;
import vincenzomola.entities.Order;
import vincenzomola.entities.Product;
import vincenzomola.enums.Category;
import vincenzomola.enums.Status;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {

        List<Product> prodotti = List.of(new Product(11111, "passeggino", Category.BABY, 15.30),
                new Product(22222, "pennarelli", Category.BABY, 2.46),
                new Product(33333, "lego", Category.BABY, 10.50),
                new Product(44444, "signore degli anelli", Category.BOOKS, 20.49),
                new Product(55555, "agatha christie", Category.BOOKS, 100.10),
                new Product(66666, "IT", Category.BOOKS, 120.99),
                new Product(77777, "palla da calcio", Category.BOYS, 5.33),
                new Product(88888, "ps5", Category.BOYS, 400),
                new Product(99999, "camicia", Category.BOYS, 129.90));


        Customer aldo = new Customer(150, "aldo", 1);
        Customer luca = new Customer(140, "luca", 2);
        Customer fabrizio = new Customer(805, "fabrizio", 1);
        Customer antonio = new Customer(648, "antonio", 3);
        Customer paolo = new Customer(929, "paolo", 2);
        Customer lucia = new Customer(292, "lucia", 2);
        Customer federica = new Customer(449, "federica", 3);
        Customer luisa = new Customer(682, "luisa", 3);
        Customer nicolo = new Customer(243, "nicolo", 2);
        Customer fede = new Customer(514, "fede", 1);

        List<Order> ordini = List.of(
                new Order(42383, Status.CONSEGNATO, LocalDate.of(2021, 2, 1), LocalDate.of(2021, 2, 10),
                        List.of(prodotti.get(0), prodotti.get(6)), lucia),
                new Order(72521, Status.ELABORAZIONE, LocalDate.of(2021, 8, 14), LocalDate.of(2021, 8, 20),
                        List.of(prodotti.get(1), prodotti.get(5)), luca),
                new Order(54416, Status.CONSEGNATO, LocalDate.of(2021, 3, 1), LocalDate.of(2021, 3, 4),
                        List.of(prodotti.get(2), prodotti.get(1)), aldo),
                new Order(21868, Status.SPEDITO, LocalDate.of(2021, 4, 10), LocalDate.of(2021, 4, 24),
                        List.of(prodotti.get(3), prodotti.get(3)), fabrizio),
                new Order(89141, Status.TRANSITO, LocalDate.of(2021, 3, 27), LocalDate.of(2021, 4, 8),
                        List.of(prodotti.get(4), prodotti.get(2)), antonio),
                new Order(28813, Status.CONSEGNATO, LocalDate.of(2021, 2, 9), LocalDate.of(2021, 2, 20),
                        List.of(prodotti.get(5), prodotti.get(6)), luisa),
                new Order(34390, Status.ELABORAZIONE, LocalDate.of(2021, 10, 11), LocalDate.of(2021, 11, 3),
                        List.of(prodotti.get(6), prodotti.get(7)), federica),
                new Order(35054, Status.SPEDITO, LocalDate.of(2021, 3, 16), LocalDate.of(2021, 3, 20),
                        List.of(prodotti.get(8), prodotti.get(2)), paolo),
                new Order(63212, Status.TRANSITO, LocalDate.of(2021, 12, 12), LocalDate.of(2021, 12, 19),
                        List.of(prodotti.get(7), prodotti.get(4)), nicolo),
                new Order(55912, Status.ELABORAZIONE, LocalDate.of(2021, 2, 27), LocalDate.of(2021, 3, 7),
                        List.of(prodotti.get(6), prodotti.get(2)), fede)
        );


        //****************************************************** PARTE 1  
        // ***************************************************************************

        List<Product> expensiveBooks = prodotti.stream()
                .filter(prodotto -> prodotto.getCategory()
                        .equals(Category.BOOKS) && prodotto.getPrice() > 100)
                .toList();

//        ordini.forEach(order -> System.out.println(order.getProducts()));

        List<Order> babyOrders = ordini.stream()
                .filter(ordine -> ordine.getProducts()
                        .stream()
                        .anyMatch(product -> product.getCategory()
                                .equals(Category.BABY)))
                .toList();
//
//        babyOrders.forEach(order -> System.out.println(order.getCustomer()
//                .getName()));

        List<Product> boysProduct = prodotti.stream()
                .filter(product -> product.getCategory()
                        .equals(Category.BOYS))
                .map(product -> {
                    product.setPrice(product.getPrice() * 0.90);
                    return product;
                })
                .toList();

        List<Order> tier2Order = ordini.stream()
                .filter(order -> order.getCustomer()
                        .getTier() == 2 && order.getOrderDate()
                        .isAfter(LocalDate.of(2021, 2, 1)) && order.getOrderDate()
                        .isBefore(LocalDate.of(2021, 4, 1)))
                .toList();

        List<Product> tier2Products = tier2Order.stream()
                .flatMap(order -> order.getProducts()
                        .stream())
                .toList();

//        System.out.println(tier2Products);

        //****************************************************** PARTE 2 
        // ***************************************************************************

        // ESERCIZIO 1 ******************************************************************************

        Map<Customer, List<Order>> customerOrders = ordini.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));
//        customerOrders.forEach((cliente, ordine) -> System.out.println(cliente + "Acquisti: " + ordine));

        // ESERCIZIO 2 ******************************************************************************

        Map<Customer, Double> customerSpesaTotale = ordini.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(order -> order.getProducts()
                        .stream()
                        .mapToDouble(Product::getPrice)
                        .sum())));
//        customerSpesaTotale.forEach(
//                (cliente, spesaTotale) -> System.out.println(cliente + "Spesa Totale: " + spesaTotale));


        // ESERCIZIO 3 **************************************************************************

        List<Product> expensiveProduct = prodotti.stream()
                .sorted(Comparator.comparing(Product::getPrice)
                        .reversed())
                .toList();
//        System.out.println(expensiveProduct);

        // ESERCIZIO 4 **************************************************************************

        OptionalDouble totalAverage = ordini.stream()
                .mapToDouble(Order::calculateTotal)
                .average();
//        if (totalAverage.isPresent()) System.out.println(totalAverage);
//        else System.out.println("MEDIA NON CALCOLABILE");
    }
}
