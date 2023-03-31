package sk.textprocessor.arguments;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.w3c.dom.Text;
import sk.textprocessor.exceptions.InvalidParametersCombinationException;
import sk.textprocessor.exceptions.InvalidTextProcessingTypeException;
import sk.textprocessor.exceptions.UnknownParametersException;
import sk.textprocessor.processing.TextProcesses;

public class ArgumentParser {
    TextProcesses TextProcesses = new TextProcesses();

//    parameters for TextProcesses

    @Parameter(names = "-lemma", description = "Lematize text")
    private static boolean lemmatize = false;

    @Parameter(names = "-analyze", description = "Morphological analyze")
    private static boolean analyze = false;
    @Parameter(names = "-token", description = "Tokenize text")
    private static boolean tokenize = false;

    @Parameter(names = "-extsents", description = "Extract sentences")
    private static boolean extractSentences = false;

    @Parameter(names = "-lowercase", description = "lowercase")
    private static boolean lowercasing = false;

//    file parameters
    @Parameter(names = "-input", description = "Input file")
    private static String inputFile = "";

    @Parameter(names = "-print", description = "Print text")
    private static boolean printText = false;

    @Parameter(names = "-change", description = "Change file")
    private static boolean changeFile = false;

    @Parameter(names = "-newFile", description = "Create new file")
    private static String newFile = "";

//  Jcommander instance
    public ArgumentParser(String[] args) throws InvalidParametersCombinationException,UnknownParametersException {
        JCommander jCommander = JCommander.newBuilder()
                .addObject(this)
                .build();
        try {
            jCommander.parse(args);
            if(checkNumberOfParameters() >  1) {
                throw new InvalidParametersCombinationException("Chyba: Nemôžete zadať súčasne dva parametre na spracovanie textu");
            }
            if(checkNumberOfParameters() == 0){
                throw new UnknownParametersException("Chyba: Nezadaly ste žiadny parameter na spracovanie textu");
            }
            }
         catch (ParameterException e) {
            throw new UnknownParametersException("Chyba: Zadane parametre neexistuju");
        }
    }

    public int checkNumberOfParameters(){
        boolean[] options = {tokenize, extractSentences,analyze,lemmatize};
        int count = 0;
        for (int i = 0; i < options.length; i++) {
            if (options[i]) {
                count++;
            }
        }
        return count;

    }

    public <T> T processTextArgument(String text) throws InvalidTextProcessingTypeException {
        try{
            if (this.isTokenize()) {
                return (T) TextProcesses.tokenize(text);
            } else if (this.isExtractSentences()) {
                return (T) TextProcesses.extractSentences(text);
            } else if (this.isAnalyze()) {
                return (T) TextProcesses.analyze(text);
            } else if (this.isLemmatize()) {
                return (T) TextProcesses.lemmatize(text);

            } else {
                return null;
            }
        }

        catch (Exception e) {
            throw new InvalidTextProcessingTypeException("Chyba: Funkcia processTextArguments nemože spracovať dany process");
        }
    }

    public String[]  TextArgument(String text){
        if(this.isTokenize()) {
            return TextProcesses.tokenize(text);
        }else if(this.isExtractSentences()){
            return TextProcesses.extractSentences(text);
        }
        String[] emptyArray = {};
        return emptyArray;
    }

//  functions
    public static String getNewFileName() { return newFile;}

    public static boolean isLemmatize() {
        return lemmatize;
    }
    public static boolean isAnalyze() {
        return analyze;
    }
    public static boolean isTokenize() {
        return tokenize;
    }
    public static boolean isExtractSentences() {
        return extractSentences;
    }
    public static boolean isChangeFile() {
        return changeFile;
    }
    public static String getInputFile() {
        return inputFile;
    }
    public static boolean isPrintText() {
        return printText;
    }
    public static boolean isLowerCasing() {
        return lowercasing;
    }


}
