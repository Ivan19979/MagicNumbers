package com.magic.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Question {

    private int deep;
    private List<OperationType> answers = new ArrayList<OperationType>(); //сохраняем ответы
    private int[] numbers;
    private int result;

    private String questionString; // пример привиденный в формат типа "X ? Y = Z"

    public Question(int deep) {
        this.deep = deep;
    }
    public String generateQuestion(){
        answers.clear();
        this.numbers = new int[deep + 1];

        for (int currNumber = 0; currNumber < this.numbers.length; currNumber++) {
            numbers[currNumber] = 1 + (int)(Math.random() * 100);
            System.out.println(numbers[currNumber]);
        }
        // генерируем знаки и записываем их в ответы
        for(int currDeep = 0; currDeep < deep; currDeep++){
            OperationType operationType = chooseOperation((int)(Math.random()*3));
            System.out.println(operationType.toString());
            this.answers.add(operationType);
        }


        Stack<Integer> numbersStack = new Stack<Integer>();
        Stack<OperationType> operationStack = new Stack<OperationType>();

        numbersStack.push(this.numbers[0]);
        for(int pointer = 0; pointer < deep; pointer++) {
            if (!operationStack.empty()) {
                OperationType operationType = operationStack.peek();
                int operationInStack = 2;
                if (operationType == OperationType.MINUS || operationType == OperationType.PLUS) {
                    operationInStack = 1;
                }
                int currentOperation = 2; //определяем вес знака который смотрим сейчас
                if (answers.get(pointer) == OperationType.MINUS || answers.get(pointer) == OperationType.PLUS) {
                    currentOperation = 1;
                }

                if (currentOperation == operationInStack || operationInStack > currentOperation) {
                    int valueInStack = numbersStack.pop(); // берем верхнее число с удалением из стека
                    int secondValueInStack = numbersStack.pop(); // берем верхнее число с удалением из стека. дальше выполняем нужную операцию и добавляем результат в стек
                    switch (operationStack.pop()) {
                        case MULTIPLY:
                            numbersStack.push(valueInStack * secondValueInStack);
                            break;
                        case MINUS:
                            numbersStack.push(secondValueInStack - valueInStack);
                            break;
                        case PLUS:
                            numbersStack.push(secondValueInStack + valueInStack);
                            break;
                    }
                }
                operationStack.push(answers.get(pointer));

            } else {
                operationStack.push(answers.get(pointer));
            }
            numbersStack.push(numbers[pointer + 1]);
        }

               while(!operationStack.empty()){
            int valueInStack = numbersStack.pop();
            int secondValueInStack = numbersStack.pop();
            switch (operationStack.pop()) {
                case MINUS:
                    numbersStack.push(secondValueInStack - valueInStack);
                    break;
                case PLUS:
                    numbersStack.push(secondValueInStack + valueInStack);
                    break;
                case MULTIPLY:
                    numbersStack.push(valueInStack * secondValueInStack);
                    break;
            }
        }
        this.result = numbersStack.pop();
        this.questionString = this.generateString();
        return this.questionString;
    }
    // генерим строку
    public String generateString(){
        StringBuilder resultString = new StringBuilder();
        resultString.append(numbers[0]);
        resultString.append(" ? ");
        for (int currNumber = 1; currNumber < numbers.length - 1; currNumber++) {
            resultString.append(numbers[currNumber]);
            resultString.append(" ? ");
        }
        resultString.append(numbers[deep]);
        resultString.append(" = ");
        resultString.append(this.result);
        return resultString.toString();
    }

    public boolean checkAnswers(List<OperationType> answers){
        int realAnswers = this.answers.size();
        int userAnswers = answers.size();
        if(realAnswers != userAnswers ){
            return false;
        }
        for(int currAnswer = 0; currAnswer < realAnswers; currAnswer++){
            if(!this.answers.get(currAnswer).equals(answers.get(currAnswer))){
                return false;
            }
        }
        return true;
    }

    private OperationType chooseOperation(int randomNumber){
        switch (randomNumber) {
            case 0:
                return OperationType.MINUS;
            case 1:
                return OperationType.PLUS;
            case 2:
                return OperationType.MULTIPLY;
            default:
                return OperationType.PLUS;
        }
    }

    public String getQuestionString() {
        return questionString;
    }
}
