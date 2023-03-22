package dmytro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

public class ArrayReflection {
    public static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        int[] dimensions = initDimensions(0);

        System.out.println("Enter the full name of the class or a primitive type:");
        String classString = scanLine();
        Object arr;
        try {
            arr = getArray(classString, dimensions);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");
            return;
        }

        int choice = 0;
        while (choice != 3) {
            menu();
            choice = scan();
            switch (choice) {
                case 1 -> {
                    dimensions = initDimensions(dimensions.length);
                    arr = setSize(arr, dimensions);
                }
                case 2 -> System.out.println(showArray(arr));
            }
        }
        in.close();
    }

    public static void menu() {
        System.out.println("1.Change size");
        System.out.println("2.Show");
        System.out.println("3.Exit");
    }

    public static int getDimension(Object array) {
        String classString = array.getClass().toString();
        int dimension = 0;

        for (int i = 0; i < classString.length(); i++) {
            if (classString.charAt(i) == '['){
                dimension++;
            }
        }
        return dimension;
    }


    public static Class<?> getClass(Object array) throws ClassNotFoundException {
        String s = array.getClass().toString();
        if (s.indexOf(';') != -1) {
            s = s.substring(s.indexOf('L') + 1, s.indexOf(';'));
            return Class.forName(s);
        } else {
            return getSimpleClass(s.charAt(s.length() - 1));
        }
    }

    public static Object getArray (Class<?> cls, int[] len) {
        return Array.newInstance(cls, len);
    }

    public static Object getArray(String cls, int[] len) throws ClassNotFoundException {
        Class<?> simpleClass;
        return getArray((simpleClass = getSimpleClass(cls)) == null ? Class.forName(cls) : simpleClass, len);
    }

    public static Class<?> getSimpleClass(String string) {
        return switch (string) {
            case "int" -> int.class;
            case "double" -> double.class;
            case "long" -> long.class;
            case "float" -> float.class;
            case "short" -> short.class;
            case "char" -> char.class;
            case "boolean" -> boolean.class;
            default -> null;
        };
    }


    public static Class<?> getSimpleClass(Character letter) {
        return switch (letter) {
            case 'I' -> int.class;
            case 'D' -> double.class;
            case 'J' -> long.class;
            case 'F' -> float.class;
            case 'S' -> short.class;
            case 'C' -> char.class;
            case 'Z' -> boolean.class;
            default -> null;
        };
    }


    public static Object setSize(Object array, int[] newDimension) throws ClassNotFoundException {
        String s;
        Object newArray;
        int height = Math.min(newDimension[0], Array.getLength(array));

        if(getDimension(array) == 2) {
            String exam = String.valueOf(array.getClass().getComponentType());
            if(exam.length() != 8) { // кол-во символов в типе (в массиве примитивных типов 8 символов)
                s = String.valueOf(array.getClass().getComponentType()).substring(8);
                s = s.substring(0, s.length() - 1);
                newArray = getArray(s, newDimension);
            }
            else {
                newArray = getArray(getClass(array).getName(), newDimension);
            }

            int width;

            for (int i = 0; i < height; i++) {
                width = Math.min(newDimension[1], Array.getLength(Array.get(array, i)));

                System.arraycopy(Array.get(array, i), 0, Array.get(newArray, i), 0, width);
            }
        }
        else {
            String exam = String.valueOf(array.getClass().getComponentType());
            if(exam.startsWith("class")) {
                s = String.valueOf(array.getClass().getComponentType()).substring(6);
                newArray = getArray(s, newDimension);
            }
            else {
                newArray = getArray(getClass(array).getName(), newDimension);
            }
            System.arraycopy(newArray, 0, array, 0, height);
        }
        return newArray;
    }

    static String showArray(Object arr) {
        StringBuilder sb = new StringBuilder();
        if(arr.getClass().isArray()){
            Class<?> componentType = arr.getClass().getComponentType();
            int coll = Array.getLength(arr);
            if(componentType.isArray()){
                sb.append(componentType.getComponentType()).append(" [").append(coll).append("]");
                Object arrRow = Array.get(arr, 0);
                int rows = Array.getLength(arrRow);
                sb.append("[").append(rows).append("] = {");
                for(int i = 0; i < coll; i++){
                    sb.append("{");
                    for(int j = 0; j < rows; j++) {
                        sb.append(Array.get(arrRow, j));
                        if(j != Array.getLength(arrRow) - 1) sb.append(", ");
                    }
                    sb.append("}");
                    if(i != Array.getLength(arr) - 1) sb.append(", ");
                }
            }
            else{
                sb.append(componentType).append(" [").append(coll).append("] = ");
                sb.append("{");
                for(int i = 0; i < Array.getLength(arr); i++){
                    sb.append(Array.get(arr, i));
                    if(i != Array.getLength(arr) - 1) sb.append(", ");
                }
            }
            sb.append("}");
        }
        return sb.toString();
    }

    public static int[] initDimensions(int mod) {
        int[] dimension;
        if (mod == 0) {
            System.out.print("Enter the number of dimensions of the array (1, 2): ");
            dimension = new int[scan()];
        }
        else if (mod == 1)
            dimension = new int[1];
        else
            dimension = new int[2];

        if (dimension.length > 2) {
            System.err.println("Incorrect data.");
            System.exit(0);
        }

        for (int i = 0; i < dimension.length; i++) {
            System.out.print("Enter size " + (i + 1) + ": ");
            dimension[i] = scan();
        }

        return dimension;
    }

    public static int scan() {
        int ch = 0;
        boolean flag = true;
        while (flag) {
            flag = false;
            try {
                ch = Integer.parseInt(in.readLine());
            } catch (NumberFormatException | IOException e){
                flag = true;
                System.err.println("Incorrect number format");
            }
        }
        return ch;
    }

    public static String scanLine(){
        String inputChoice = "";
        try {
            inputChoice = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputChoice;
    }


}
