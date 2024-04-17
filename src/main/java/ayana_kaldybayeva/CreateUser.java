package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.City;
import ayana_kaldybayeva.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.Scanner;

public class CreateUser {
    public static void main(String[] args) {

        //Создать нового пользователя с возможность выбрать город и вписать остальную
        //информацию.

        EntityManagerFactory factory = EntityManagerFactoryHolder.factory();
        EntityManager manager = factory.createEntityManager();

        System.out.println("Вы собираетесь создать нового пользователя.");
        System.out.println("Введите логин");

        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();

        System.out.println("Введите имя");
        String name = scanner.nextLine();

        TypedQuery<City> cityTypedQuery =manager.createQuery(
                "select c from City c", City.class
        );

        System.out.println("Доступные города");
        for (int i = 0; i < cityTypedQuery.getResultList().size(); i++){
            System.out.println(i + 1 + ". " + cityTypedQuery.getResultList().get(i).getName());
        }

        try {
            manager.getTransaction().begin();

            System.out.println("Выберите номер города");
            int cityNumber = Integer.parseInt(scanner.nextLine());
            City chosenCity = cityTypedQuery.getResultList().get(cityNumber - 1);

            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setCity(chosenCity);
            manager.persist(user);

            System.out.println("Пользователь создан");
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
