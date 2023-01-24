package theDragonkin.util;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeEnergyHelper {
    public static EnumMap<Mana, Integer> currentMana = new EnumMap<Mana, Integer>(Mana.class);

    public static int getManaByEnum(Mana mana){
        return currentMana.getOrDefault(mana, 0);
    }
    public static void setManaByEnum(Mana mana, int amount){
        currentMana.put(mana, amount);
    }

    public static EnumMap<Mana, Boolean> hasEnoughMana(EnumMap<Mana, Integer> empowerCosts){
        Map<Mana, Integer> temp = currentMana.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        EnumMap<Mana, Boolean> res = new EnumMap<Mana, Boolean>(Mana.class);
        for(Map.Entry<Mana,Integer> e : empowerCosts.entrySet()){
            if(e.getKey()!= Mana.Colorless){
                if(temp.getOrDefault(e.getKey(), 0)>=e.getValue()){
                    temp.put(e.getKey(), temp.getOrDefault(e.getKey(), 0)-e.getValue());
                    res.put(e.getKey(),true);
                }else if (e.getValue() == -1 && temp.getOrDefault(e.getKey(), 0)>=0) {
                    res.put(e.getKey(),true);
                } else {
                    res.put(e.getKey(),false);
                }
            }
        }
        int colorlessCost = empowerCosts.getOrDefault(Mana.Colorless, 0);
        if(colorlessCost > 0){
            if(temp.isEmpty()){
                res.put(Mana.Colorless, false);
            } else {
                while (colorlessCost > 0) {
                    Map.Entry<Mana, Integer> tempCost = temp.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get();
                    if (tempCost.getValue() > 0) {
                        temp.put(tempCost.getKey(), tempCost.getValue() - 1);
                        colorlessCost--;
                        res.put(Mana.Colorless, true);
                    } else {
                        res.put(Mana.Colorless, false);
                        break;
                    }
                }
            }
        }
        return res;
    }
    public static boolean handleElementalCosts(EnumMap<Mana, Integer> empowerCosts){
        Map<Mana, Integer> temp = currentMana.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        for(Map.Entry<Mana,Integer> e : empowerCosts.entrySet()){
            if(e.getKey()!= Mana.Colorless){
                if(temp.getOrDefault(e.getKey(), 0)>=e.getValue()){
                    temp.put(e.getKey(), temp.get(e.getKey())-e.getValue());
                } else if (e.getValue() == -1 && temp.getOrDefault(e.getKey(), 0)>=0) {
                    temp.put(e.getKey(), temp.get(e.getValue()));
                } else {
                    return false;
                }
            }
        }
        int colorlessCost = empowerCosts.getOrDefault(Mana.Colorless, 0);
        if(colorlessCost > 0){
            if(temp.isEmpty()){
                return false;
            } else {
                while(colorlessCost>0) {
                    Map.Entry<Mana, Integer> tempCost = temp.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue)).get();
                    if (tempCost.getValue() > 0) {
                        temp.put(tempCost.getKey(), tempCost.getValue() - 1);
                        colorlessCost--;
                    } else {
                        return false;
                    }
                }
            }
        }
        currentMana.putAll(temp);
        return true;
    }

    public enum Mana{
        Colorless,
        Exalt
    }
}
