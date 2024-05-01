package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.City;
import ayana_kaldybayeva.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

public class CreateUser {
    public static void create() {

        //Создать нового пользователя с возможность выбрать город и вписать остальную
        //информацию.

        EntityManagerFactory factory = EntityManagerFactoryHolder.factory();
        EntityManager manager = factory.createEntityManager();


        try {
            manager.getTransaction().begin();

            System.out.println("Вы собираетесь создать нового пользователя.");

            Scanner scanner = new Scanner(System.in);
            boolean uniqueLogin = false;

            while (!uniqueLogin) {
                System.out.println("Введите логин");
                String loginOfTheNewUser = scanner.nextLine();

                TypedQuery<Long> query = manager.createQuery(
                        "select count(u.id) from User u where u.login = ?1", Long.class
                );
                query.setParameter(1, loginOfTheNewUser);
                Long singleResult = query.getSingleResult();

                if (singleResult != 0L) {
                    System.out.println("Такой пользователь уже есть");
                } else {
                    uniqueLogin = true;
                    User user = new User();
                    user.setLogin(loginOfTheNewUser);

                    System.out.println("Введите имя");
                    String name = scanner.nextLine();

                    while (!name.matches("[a-zA-Z]+") || name.isEmpty()) {
                        System.out.println("Введите правильное имя");
                        name = scanner.nextLine();
                    }

                    TypedQuery<City> cityTypedQuery = manager.createQuery(
                            "select c from City c", City.class
                    );

                    System.out.println("Доступные города");
                    for (int i = 0; i < cityTypedQuery.getResultList().size(); i++) {
                        System.out.println(i + 1 + ". " + cityTypedQuery.getResultList().get(i).getName());
                    }
                    boolean city = false;
                    List<City> cities = cityTypedQuery.getResultList();

                    while (!city) {
                        System.out.println("Выберите номер города");
                        int cityNumber;

                        try {
                            cityNumber = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
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

                    user.setName(name);
                    manager.persist(user);

                    System.out.println("Пользователь создан");
                    manager.getTransaction().commit();

                }
            }
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
