package sk.textprocessor.processing;

import sk.textprocessor.arguments.ArgumentParser;
import sk.textprocessor.output.FileHandler;
import cz.cuni.mff.ufal.morphodita.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.util.*;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextProcesses {


    //    tokenization
    public String[] tokenize(String text){

        String output = "";
        String[] array;
        String punctuation = ".,<>/\\\"\'}{[]|!@#$%^&*()_-=+:;?`~";
        for (char s: punctuation.toCharArray()){
            String charakter = Character.toString(s);
            text = text.replace(charakter," "+charakter+" ");
        }

        array = text.split(" ");

        // prejdeme cez kazdy prvok a odstranime potencialne space okolo punctuation
        for(int i = 0; i < array.length; i++) {
            array[i] = array[i].replaceAll("\\s+","").trim();
        }

        if(ArgumentParser.isLowerCasing()){
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i].toLowerCase();
        }
    }
        return array;
    }

//    extractSentences
    public String[] extractSentences(String text) {
        ArrayList<String> sentences = new ArrayList<String>();

        Abbreviation skr = new Abbreviation();
        boolean dictionary = false;

        int sentenceLastChar = 0;
        int wordLastChar = 0;
        String word = "";
        String input = text;

        sentenceLastChar = 0;
        wordLastChar = 0;

        for (int i = 1; i < input.length() - 3; i++) {
            String ch = input.substring(i, i + 3);

            if (input.charAt(i + 2) == ' ') {
                word = input.substring(wordLastChar, i + 2).trim().toLowerCase();
                wordLastChar = i + 3;
                dictionary = skr.isAbbreviation(word);
            }

            if ((ch.matches("([!?.])(\\s)[A-Z]")) && !dictionary && (input.substring(i-1,i).matches("[a-z]") )) {
                sentences.add(input.substring(sentenceLastChar, i + 1));
                sentenceLastChar = i + 1;
            }
        }

        String[] sentenceArray = new String[sentences.size()];
        sentenceArray = sentences.toArray(sentenceArray);


        if(ArgumentParser.isLowerCasing()){
            for (int i = 0; i < sentenceArray.length; i++) {
                sentenceArray[i] = sentenceArray[i].toLowerCase();
            }
        }

        return sentenceArray;
    }


    public String[] lemmatize(String text) throws Exception {
        // Načítanie modelu
        String modelPath = "src/taggers/slovak-morfflex-pdt-170914.tagger";
        Tagger tagger = Tagger.load(modelPath);

        String dictPath = "src/taggers/slovak-morfflex-170914.dict";
        Morpho morpho = Morpho.load(dictPath);

        // Lematizácia každého slova v texte
        List<String> words = Arrays.asList(this.tokenize(text));
        String[] lemmasArray = new String[words.size()];
        int i = 0;
        for (String word : words) {
            TaggedLemmas lemmas = new TaggedLemmas();
            Forms forms = new Forms();
            forms.add(word);
            tagger.tag(forms, lemmas);
            String lemma = lemmas.get(0).getLemma();
            List<String> rawLemmas = Collections.singletonList(morpho.rawLemma(lemma));
            String rawLemma = rawLemmas.get(0);
            lemmasArray[i++] = rawLemma;
        }

        if(ArgumentParser.isLowerCasing()){
            for (int k = 0; i < lemmasArray.length; i++) {
                lemmasArray[k] = lemmasArray[k].toLowerCase();
            }
        }

        return lemmasArray;
    }





    public LinkedHashMap<String, String> analyze(String text) throws Exception {

        String modelPath = "src/taggers/slovak-morfflex-pdt-170914.tagger";
        Tagger tagger = Tagger.load(modelPath);

        String dictPath = "src/taggers/slovak-morfflex-170914.dict";
        Morpho morpho = Morpho.load(dictPath);


        List<String> words = Arrays.asList(this.tokenize(text));
        LinkedHashMap<String, String> tags = new LinkedHashMap<>();
        for (String word : words) {
            TaggedLemmas lemmas = new TaggedLemmas();
            Forms forms = new Forms();
            forms.add(word);
            tagger.tag(forms, lemmas);
            String tag = lemmas.get(0).getTag();
            if (!tag.contains("X@")) {
                tags.put(word, tag);
            }
        }

        if (ArgumentParser.isLowerCasing()) {
            for (Map.Entry<String, String> entry : tags.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toLowerCase();
                tags.put(key, value);
            }
        }

        return tags;
    }









    public String convertProcessText(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i < strings.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }


}








