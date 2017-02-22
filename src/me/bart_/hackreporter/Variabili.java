package me.bart_.hackreporter;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.*;

/**
 * Created by Bart_ on 02/10/2016.
 */
public class Variabili {

    public static List<String> reportnotsee = new ArrayList();


    //SALVE TUTTI I PLAYER CON "NOMEPLAYER.ReportedSee" == FALSE, QUINDI ANCORA DA CONTROLLARE
    public static boolean lookfile () {

        reportnotsee.clear();
        int i = 0;

        for (String key : FilesCreate.reports.getKeys(false)) {
            if (FilesCreate.reports.getBoolean(key + ".ReportedSee") == false) {
                reportnotsee.add(i, key);
                i++;
            }
        }
        return false;
    }


}
