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

                TypedQuery<User> query = manager.createQuery(
                        "select u from User u where u.login = ?1", User.class
                );
                query.setParameter(1, loginOfTheNewUser);
                List<User> userList = query.getResultList();

                if (!userList.isEmpty()) {
                    System.out.println("Такой пользователь уже есть");
                } else {
                    uniqueLogin = true;
                    User user = new User();
                    user.setLogin(loginOfTheNewUser);

                    System.out.println("Введите имя");
                    String name = scanner.nextLine();

                    TypedQuery<City> cityTypedQuery =manager.createQuery(
                            "select c from City c", City.class
                    );

                    System.out.println("Доступные города");
                    for (int i = 0; i < cityTypedQuery.getResultList().size(); i++){
                        System.out.println(i + 1 + ". " + cityTypedQuery.getResultList().get(i).getName());
                    }
                    System.out.println("Выберите номер города");
                    int cityNumber = Integer.parseInt(scanner.nextLine());
                    City chosenCity = cityTypedQuery.getResultList().get(cityNumber - 1);

                    user.setName(name);
                    user.setCity(chosenCity);
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
