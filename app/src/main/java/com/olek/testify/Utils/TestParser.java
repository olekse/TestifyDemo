package com.olek.testify.Utils;


import com.olek.testify.model.Answer;
import com.olek.testify.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestParser {
    public static Map<Task, List<Answer>> parseTask(List<String> lines) {
        Map<Task, List<Answer>> resultMap = new HashMap<>();


        Task currentTask = null;
        List<Answer> currentAnswers = new ArrayList<>();

        for (String line : lines) {

            if (line.equals("")) continue;

            if (isD(line.charAt(0))) { // if this then it's a question

                try {

                    if (currentTask != null) {
                        resultMap.put(currentTask, currentAnswers);
                        currentTask = null;
                        currentAnswers = new ArrayList<>();
                    }

                    int digitLength = 1;
                    StringBuilder numberSb = new StringBuilder();

                    numberSb.append(line.charAt(0));

                    while (isD(line.charAt(digitLength))) {
                        numberSb.append(line.charAt(digitLength));
                        digitLength++;
                        if (digitLength == line.length() - 1) {
                            continue;
                        }
                    }
                    Integer finalNumber = Integer.parseInt(numberSb.toString());

                    int counter = digitLength;

                    while (!Character.isUpperCase(line.charAt(counter))) {
                        counter++;
                        if (counter == line.length() - 1) {
                            continue;
                        }
                    }

                    String question = line.substring(counter);

                    currentTask = new Task();
                    currentTask.setNumber(finalNumber);
                    currentTask.setText(question);

                } catch (Exception ex) {
                    currentTask = null;
                    currentAnswers = new ArrayList<>();
                }

            } else { // must be an answer

                try {

                    int index = 0;

                    while (!Character.isUpperCase(line.charAt(index))) {
                        index++;
                        if (index == line.length() - 1) {
                            continue;
                        }
                    }

                    Character currentAnswerLabel = line.charAt(index);
                    ++index;

                    while (!Character.isLetter(line.charAt(index))) {
                        index++;
                        if (index == line.length() - 1) {
                            continue;
                        }
                    }

                    // now index is the start of the answer

                    boolean isCorrect = line.endsWith(" c") || line.endsWith(" C");

                    Answer tmp = new Answer();
                    tmp.setCorrect(isCorrect);
                    tmp.setAnswerText(line.substring(index));
                    tmp.setKeyCode(new String(currentAnswerLabel + ""));
                    currentAnswers.add(tmp);

                } catch (Exception ex) {
                    continue;
                }

            }
        }

        if (currentTask != null) {
            resultMap.put(currentTask, currentAnswers);
        }


        return resultMap;
    }

    private static boolean isD(Character c) {
        return Character.isDigit(c);
    }

    private static void parseTask(String d) {

    }

}
