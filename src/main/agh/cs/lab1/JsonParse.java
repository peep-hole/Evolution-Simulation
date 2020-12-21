package agh.cs.lab1;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.IOException;

public class JsonParse {

    public final long width;
    public final long height;
    public final long startEnergy;
    public final long moveEnergy;
    public final long plantEnergy;
    public final double jungleRatio;

    public JsonParse(String filename) throws IOException, ParseException {

        // PARSING PARAMETERS FROM JSON

        Object object = new JSONParser().parse(new FileReader(filename));
        JSONObject jsonObject = (JSONObject)object;

        // SIMULATION PARAMETERS

        width = (long) jsonObject.get("width");
        height = (long)jsonObject.get("height");
        startEnergy = (long)jsonObject.get("startEnergy");
        moveEnergy = (long)jsonObject.get("moveEnergy");
        plantEnergy = (long)jsonObject.get("plantEnergy");
        jungleRatio = (double)jsonObject.get("jungleRatio");

        // CHECKING PARAMETERS

        if(       (width <= 0)
                ||(height <=0)
                ||(startEnergy < 0)
                ||(moveEnergy < 0)
                ||(plantEnergy < 0)
                || jungleRatio < 0) {
            throw new IllegalArgumentException("incorrect arguments passed to json");
        }
    }


}
