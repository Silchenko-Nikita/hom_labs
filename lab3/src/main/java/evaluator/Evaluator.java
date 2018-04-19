package evaluator;

import datatypes.Data;
import datatypes.Matrix;
import datatypes.Numeric;
import datatypes.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {
    HashMap<String, Data> vars = new HashMap<>();
    HashMap<String, String> quotes = new HashMap<>();

    private final String identifier = "$";

    private final String numericString = "(-?\\d+\\.?\\d*)";
    private final Pattern numericPattern = Pattern.compile(numericString);

    private final String vectorString = String.format("(\\[(%s,*)*%s\\])", numericString, numericString);
    private final Pattern vectorPattern = Pattern.compile(vectorString);

    private final String  matrixString = String.format("(\\[(%s,*)*%s\\])", vectorString, vectorString);
    private final Pattern matrixPattern = Pattern.compile(matrixString);

    private final String variableString = "([a-zA-Z]+\\d*)";
    private final Pattern variablePattern = Pattern.compile(variableString);

    private final String quoteMaskString = "\\$\\d+";
    private final Pattern quoteMaskPattern = Pattern.compile(quoteMaskString);

    private final String operandString = String.format("(%s|%s|%s|%s)", numericString, variableString,
            vectorString, matrixString);
    private final Pattern operandPattern = Pattern.compile(operandString);

    private final String assignString = String.format("(%s*=.*)", variableString);
    private final Pattern assignPattern = Pattern.compile(assignString);

    private final String detString = String.format("det(%s)", quoteMaskString);
    private final Pattern detPattern = Pattern.compile(detString);

    private final String assignDelimiter = "=";
    private final Pattern assignDelimiterPattern = Pattern.compile(assignDelimiter);

    private final String plusDelimiter = "\\+";
    private final Pattern plusDelimiterPattern = Pattern.compile(plusDelimiter);

    private final String multDelimiter = "\\*";
    private final Pattern multDelimiterPattern = Pattern.compile(multDelimiter);

    public Data evaluate(String input){
        return buildTree(input).evaluate();
    }

    private Component buildTree(String string){
        Component component = null;
        Matcher matcher;

        string = collapseString(string);

        if(assignPattern.matcher(string).matches()){
            component = new Composite(string, this, Operation.ASSIGN);
            String[] operands = assignDelimiterPattern.split(string);

            for (int i = 0; i < operands.length - 1; i++) {
                if (!variablePattern.matcher(operands[i]).matches()){
                    throw new IllegalArgumentException("invalid input");
                }

                component.add(new Leaf(operands[i], this, DataType.VAR));
            }
            component.add(buildTree(operands[operands.length - 1]));

        } else if(plusDelimiterPattern.matcher(string).find()){
            component = new Composite(string, this, Operation.ADD);
            String[] operands = plusDelimiterPattern.split(string);

            for (String operand: operands) {
                component.add(buildTree(operand));
            }

        } else if(multDelimiterPattern.matcher(string).find()){
            component = new Composite(string, this, Operation.MULT);
            String[] operands = multDelimiterPattern.split(string);

            for (String operand: operands) {
                component.add(buildTree(operand));
            }

        } else if ((matcher = detPattern.matcher(string)).matches()){
            component = new Composite(string, this, Operation.DET);
            component.add(buildTree(matcher.group(1)));

        } else if(quoteMaskPattern.matcher(string).matches()){
            component = new Composite(string, this, Operation.MULT);
            component.add(buildTree(quotes.get(string)));

        } else if(variablePattern.matcher(string).matches()){
            component = new Leaf(string, this, DataType.VAR);

        } else if(numericPattern.matcher(string).matches()){
            component = new Leaf(string, this, DataType.NUMERIC);

        } else if(vectorPattern.matcher(string).matches()){
            component = new Leaf(string, this, DataType.VECTOR);

        } else if(matrixPattern.matcher(string).matches()){
            component = new Leaf(string, this, DataType.MATRIX);

        }else {
            throw new IllegalArgumentException("invalid input");
        }

        return component;
    }

    Numeric evaluateNumeric(String input){
        return new Numeric(Double.parseDouble(input));
    }

    Vector evaluateVector(String input){
        Matcher matcher = numericPattern.matcher(input);

        ArrayList<String> floatsList = new ArrayList<>();
        while (matcher.find()) {
            floatsList.add(matcher.group(1));
        }

        double array[] = new double[floatsList.size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = Double.parseDouble(floatsList.get(i));
        }

        return new Vector(array);
    }

    Matrix evaluateMatrix(String input){
        Matcher vectorMatcher = vectorPattern.matcher(input);

        ArrayList<ArrayList<String>> vectorsList = new ArrayList<>();
        while (vectorMatcher.find()) {
            Matcher floatMatcher = numericPattern.matcher(vectorMatcher.group(1));

            ArrayList<String> floatsList = new ArrayList<String>();
            while (floatMatcher.find()) {
                floatsList.add(floatMatcher.group(1));
            }
            vectorsList.add(floatsList);
        }

        double array[][] = new double[vectorsList.size()][vectorsList.get(0).size()];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j< array[0].length; j++) {
                array[i][j] = Double.parseDouble(vectorsList.get(i).get(j));
            }
        }

        return new Matrix(array);
    }

    private String collapseString(String string){
        int lastTopLevelQuoteOpeningInd = 0;
        int openedQuotesNum = 0;

        StringBuilder collapsedStringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);

            if (ch == '('){
                openedQuotesNum++;

                if (openedQuotesNum == 1){
                    lastTopLevelQuoteOpeningInd = i;
                }
            } else if (ch == ')'){
                openedQuotesNum--;

                if (openedQuotesNum == 0){
                    String id = identifier + quotes.size();
                    collapsedStringBuilder.append(id);
                    quotes.put(id, string.substring(lastTopLevelQuoteOpeningInd + 1, i));
                } else if (openedQuotesNum < 0){
                    throw new IllegalArgumentException("invalid input");
                }
            } else if (openedQuotesNum == 0 && ch != ' '){
                collapsedStringBuilder.append(ch);
            }
        }

        if (openedQuotesNum > 0){
            throw new IllegalArgumentException("invalid input");
        }

        return collapsedStringBuilder.toString();
    }

    public Data getVar(String var) {
        return vars.get(var);
    }
}
