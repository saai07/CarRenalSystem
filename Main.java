import java.util.ArrayList;
import java.util.Scanner;

class Vehicle {
    String vehicleId;
    String vehicleName;
    boolean isRented;
    int rentedDays;

    public Vehicle(String vehicleId, String vehicleName) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.isRented = false;
        this.rentedDays = 0;
    }
}

class Car extends Vehicle {
    String category;

    public Car(String vehicleId, String vehicleName, String category) {
        super(vehicleId, vehicleName);
        this.category = category;
    }
}

class User {
    String username;
    String password;
    String mobileNumber;
    String address;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class Main {
    private static final int ECONOMY_DAILY_RATE = 1000;
    private static final int FAMILY_DAILY_RATE = 2000;
    private static final int PREMIUM_DAILY_RATE = 3000;

    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Car> cars = new ArrayList<>();
    private static User currentUser;

    public static void main(String[] args) {
        cars.add(new Car("eco1", "Creta", "Economy"));
        cars.add(new Car("eco2", "Honda, ", "Economy"));
        cars.add(new Car("lux1", "Thar", "Premium"));
        cars.add(new Car("fam1", "Bolero", "Family"));

        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------Hello And Welcome to TAKE A CAR ! The Best Car Rental Service In BURLA !!--------------------");
        while (true) {
            System.out.println("");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Thank you Seee u soon !");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            if (currentUser != null) {
                while (true) {
                    System.out.println("\n1. Show Cars");
                    System.out.println("2. Return");
                    System.out.println("3. Exit");
                    System.out.print("Choose an option: ");

                    int userChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (userChoice) {
                        case 1:
                            showCars(scanner);
                            break;
                        case 2:
                            returnCar(scanner);
                            break;
                        case 3:
                            System.out.println("Thank You for using our Serives !");
                            System.exit(0);
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }

                    if (currentUser == null) {
                        break;
                    }
                }
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String mobileNumber;
        do {
            System.out.print("Enter 10-digit mobile number: ");
            mobileNumber = scanner.nextLine();
            if (!mobileNumber.matches("\\d{10}")) {
                System.out.println("Invalid mobile number. Please enter a 10-digit number.");
            }
        } while (!mobileNumber.matches("\\d{10}"));

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        User newUser = new User(username, password);
        newUser.mobileNumber = mobileNumber;
        newUser.address = address;

        users.add(newUser);

        System.out.println("Registration successful.");
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                currentUser = user;
                System.out.println("Login successful. Welcome, " + currentUser.username + "!");
                return;
            }
        }

        System.out.println("Invalid username or password. Please try again.");
    }

    private static void showCars(Scanner scanner) {
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
        } else {
            System.out.println("\nAvailable Cars:");
            for (Car car : cars) {
                System.out.println("- ID: " + car.vehicleId + ", Name: " + car.vehicleName +
                        " (Category: " + car.category + ")");
            }

            System.out.print("\nEnter the car ID of the car you want to rent: ");
            String carId = scanner.nextLine();

            Car selectedCar = findCarById(carId);

            if (selectedCar != null && !selectedCar.isRented) {
                System.out.print("Enter the number of days for rental: ");
                int numDays = scanner.nextInt();

                int rentalCost = calculateRentalCost(selectedCar.category, numDays);

                System.out.println("You have rented " + selectedCar.vehicleName +
                        " (Category: " + selectedCar.category + ")");
                System.out.println("Number of days: " + numDays);
                System.out.println("Rental cost: RS " + rentalCost);

                selectedCar.isRented = true;
                selectedCar.rentedDays = numDays;

                System.out.print("Enter your mobile number: ");
                currentUser.mobileNumber = scanner.next();

                System.out.print("Enter your address: ");
                currentUser.address = scanner.nextLine();
                currentUser.address = scanner.nextLine();

                System.out.println("Thank you! Your car is located in Suiit Burla. Your car is waiting. See you soon!");
            } else if (selectedCar == null) {
                System.out.println("Invalid car ID. Please try again.");
            } else {
                System.out.println("This car is already rented. Please choose another car.");
            }
        }
    }

    private static void returnCar(Scanner scanner) {
        if (cars.isEmpty()) {
            System.out.println("No cars available for return.");
            return;
        }

        System.out.print("\nEnter the car ID of the car you want to return: ");
        String carId = scanner.nextLine();

        Car returnedCar = findCarById(carId);

        if (returnedCar != null && returnedCar.isRented) {
            int rentedDays = returnedCar.rentedDays;

            int rentalCost = calculateRentalCost(returnedCar.category, rentedDays);

            System.out.println("\nYour Invoice:");
            System.out.println("");
            System.out.println("Name: " + currentUser.username);
            System.out.println("");
            System.out.println("Mobile Number: " + currentUser.mobileNumber);
            System.out.println("");
            System.out.println("Address: " + currentUser.address);
            System.out.println("");
            System.out.println("Car Rented: " + returnedCar.vehicleName);
            System.out.println("");
            System.out.println("Category: " + returnedCar.category);
            System.out.println("");
            System.out.println("Rented Days: " + rentedDays);
            System.out.println("");
            System.out.println("Total Rental Cost: RS " + rentalCost);
            System.out.println("");
            System.out.println("Thank you for using our service!");

            returnedCar.isRented = false;
            returnedCar.rentedDays = 0;
        } else if (returnedCar == null) {
            System.out.println("Invalid car ID. Please try again.");
        } else {
            System.out.println("This car is not currently rented. Please enter a rented car ID.");
        }
    }

    private static Car findCarById(String carId) {
        for (Car car : cars) {
            if (car.vehicleId.equals(carId)) {
                return car;
            }
        }
        return null;
    }

    private static int calculateRentalCost(String category, int numDays) {
        int dailyRate = 0;

        switch (category) {
            case "Economy":
                dailyRate = ECONOMY_DAILY_RATE;
                break;
            case "Family":
                dailyRate = FAMILY_DAILY_RATE;
                break;
            case "Premium":
                dailyRate = PREMIUM_DAILY_RATE;
                break;
        }

        return dailyRate * numDays;
    }
}