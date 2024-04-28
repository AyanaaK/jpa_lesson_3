package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.City;
import ayana_kaldybayeva.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
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

                if (answer.equals("да")){
                    manager.getTransaction().begin();

                    System.out.println("Введите новое имя:");
                    String newName = scanner.nextLine();

                    System.out.println("Выберите новый город:");
                    TypedQuery<City> cityTypedQuery2 =manager.createQuery(
                            "select c from City c ", City.class
                    );
                    for (int i = 0; i < cityTypedQuery2.getResultList().size(); i++){
                        System.out.println(i + 1 + ". " + cityTypedQuery2.getResultList().get(i).getName());
                    }
                    int cityNumber = Integer.parseInt(scanner.nextLine());
                    City chosenCity = cityTypedQuery2.getResultList().get(cityNumber - 1);

                    user.setName(newName);
                    user.setCity(chosenCity);
                    manager.persist(user);
                    System.out.println("Данные обновлены");

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
