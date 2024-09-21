package ru.nsu.mikiyanskiy;

import java.io.*;
import java.util.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class MainTest {

    @Test
    void InputTest1() {
        String InputSimulate = "1\n0\n1\n0\n1\n1\n1\n0\n1\n0\n";

        InputStream originalIn = System.in;  // Сохраняем оригинальный System.in.

        try {
            // Меняем System.in на ByteArrayInputStream
            ByteArrayInputStream in = new ByteArrayInputStream(InputSimulate.getBytes());
            System.setIn(in);


            Main.main(new String[]{});

        }
        finally {
            // Восстанавливаем оригинальный ввод.
            System.setIn(originalIn);
        }
    }

}