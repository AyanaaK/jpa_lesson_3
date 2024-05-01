package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.City;
import ayana_kaldybayeva.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

public class FindUser {
    public static void find() {
        EntityManagerFactory factory = EntityManagerFactoryHolder.factory();
        EntityManager manager = factory.createEntityManager();

        try {
            System.out.println("Вы собираетесь найти нужного вам пользователя.");
            System.out.println("Введите логин");

            Scanner scanner = new Scanner(System.in);
            String login = scanner.nextLine();

            TypedQuery<User> typedQuery = manager.createQuery(
                    "select u from User u where u.login = ?1", User.class
            );
            typedQuery.setParameter(1, login);
            User user = typedQuery.getSingleResult();

            if (user != null){
                System.out.println("Имя пользователя: " + user.getName());
                System.out.println("Город пользователя: " + user.getCity().getName());

                System.out.println("Желаете поменять данные? да/нет");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("да")){
                    manager.getTransaction().begin();

                    System.out.println("Введите новое имя:");
                    String newName = scanner.nextLine();

                    while (!newName.matches("[a-zA-Z]+") || newName.isEmpty()){
                        System.out.println("Введите правильное имя");
                        newName = scanner.nextLine();
                    }

                    System.out.println("Выберите новый город:");
                    TypedQuery<City> cityTypedQuery2 =manager.createQuery(
                            "select c from City c ", City.class
                    );
                    for (int i = 0; i < cityTypedQuery2.getResultList().size(); i++){
                        System.out.println(i + 1 + ". " + cityTypedQuery2.getResultList().get(i).getName());
                    }

                    boolean city = false;
                    List<City> cities = cityTypedQuery2.getResultList();

                    while (!city) {
                        System.out.println("Выберите номер города");
                        int cityNumber;

                        try{
                            cityNumber = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e){
                            System.out.println("Введите правильный номер города");
                            continue;
                        }

                        if (cityNumber < 1 || cityNumber > cities.size()) {
                            System.out.println("Вы ввели неверный номер. Попробуйте еще раз");
                        } else {
                            City chosenCity = cities.get(cityNumber - 1);
                            user.setCity(chosenCity);
                            city = true;
                        }
                    }

                    manager.getTransaction().commit();
                } else {
                    System.out.println("Данные пользователя не изменены");
                }
            } else {
                System.out.println("Пользователь не найден");
            }
        } catch (Exception e){
            System.out.println("Пользователь не найден");
        }


    }
}
