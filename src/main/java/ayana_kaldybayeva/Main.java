package ayana_kaldybayeva;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean run = true;

        while(run){
            System.out.println("Что вы хотите сделать? Выберите номер");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Найти пользователя");
            System.out.println("3. Выйти");

            System.out.print("Номер: ");

            Scanner scanner = new Scanner(System.in);
            int actionToDo;

            try {
               actionToDo = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e){
                System.out.println("Введите правильный номер");
                continue;
            }

            switch (actionToDo) {
                case 1:
                    CreateUser.create();
                    break;
                case 2:
                    FindUser.find();
                    break;
                case 3:
                    System.out.println("Вы вышли из программы");
                    run = false;
                    break;
                default:
                    System.out.println("Неверный номер действия. Попробуйте заново");
                    break;
            }
        }

    }
}
