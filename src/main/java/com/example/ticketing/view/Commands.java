package com.example.ticketing.view;

public class Commands {

        public static void help() {
                System.out.println("\n~Commands:~\n");
                System.out.println("""
                                - add-vendor <name> <rate> : Add a vendor with the given id and rate.
                                command syntax : tsc add-vendor <name> <rate>
                                ex : tsc add-vendor john 2
                                """);

                System.out.println("""
                                - add-customer <name> <rate> : Add a customer with the given id and rate.
                                command syntax : tsc add-customer <name> <rate>
                                ex : tsc add-customer john 2
                                """);

                System.out.println("  ");

                System.out.println("""
                                - remove-vendor <id> : Remove a vendor with the given id.
                                command syntax : tsc remove-vendor <name>
                                ex : tsc remove-vendor 1
                                """);

                System.out.println("""
                                - remove-customer <id> : Remove a customer with the given id.
                                command syntax : tsc remove-customer <name>
                                ex : tsc remove-customer 1
                                """);

                System.out.println("  ");

                System.out.println("""
                                - exit : Exit the application.
                                command syntax : exit
                                """);

                System.out.println("""
                                - restart : Restart the application.
                                command syntax : restart
                                                """);

                System.out.println("  ");

                System.out.println("""
                                --view-system : View the current status of the system.
                                command syntax : tsc --view-system
                                """);

                System.out.println("""
                                --view-users : View the users of the system.
                                command syntax : tsc --view-users
                                                """);

                System.out.println("  ");

                System.out.println("""
                                --help : Display the list of commands.
                                command syntax : tsc --help
                                """);

                System.out.println(" ");

                System.out.println("""
                                - start : Start the simulation.
                                command syntax : tsc start
                                                """);

                System.out.println("""
                                - stop : Stop the simulation.
                                command syntax : tsc stop
                                                """);

        }
}
