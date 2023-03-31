package sk.textprocessor.processing;

public class Abbreviation {
    String[] abbreviations_dictionary = {
            "bc.", "icdr.", "ing.", "arch.", "sv.", "judr.", "art.", "arch.", "mgr.", "mddr.", "mudr.",
            "mvdr.", "paeddr.", "pharmdr.", "phdr.", "phmr.", "rndr.", "rsdr.", "rtdr.", "thdr.", "thlic.",
            "artd.", "csc.", "drsc.", "ph.d.", "th.d.", "doc.", "prof.", "dr. h. c.", "slob.", "des.", "čat.",
            "rt.", "rtm.", "nrtm.", "šbnrtm.", "pprap.", "prap.", "nprap.", "ppor.", "por.", "npor.", "kpt.",
            "mjr.", "pplk.", "plk.", "brig. gen.", "genmjr.", "genpor.", "arm. gen.", "gen."
 };

    public boolean isAbbreviation(String word) {
        for (String s : abbreviations_dictionary) {
            if (s.equals(word)) {
                return true;
            }
        }
        return false;
    }



}

