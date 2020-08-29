package zti.projekt;

import java.util.ArrayList;
import java.util.List;

public class Ontology {

    public static List<String> getOntologyClasses()
    {
        List<String> list = new ArrayList<>();
        list.add("http://dbpedia.org/ontology/Person");
        list.add("http://dbpedia.org/ontology/Place");
        list.add("http://dbpedia.org/ontology/Organisation");
        return list;
    }
}
