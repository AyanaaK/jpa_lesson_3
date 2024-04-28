package ayana_kaldybayeva;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Что вы хотите сделать? Выберите номер");
        System.out.println("1. Создать пользователя");
        System.out.println("2. Найти пользователя");
        System.out.println("3. Выйти");

        System.out.print("Номер: ");

        Scanner scanner = new Scanner(System.in);
        int actionToDo = Integer.parseInt(scanner.nextLine());

        switch (actionToDo) {
            case 1:
                CreateUser.create();
                break;
            case 2:
                FindUser.find();
                break;
            case 3:
                System.out.println("Вы вышли из программы");
                return;
            default:
                System.out.println("Неверный номер действия. Попробуйте заново");
                break;
        }
    }
}