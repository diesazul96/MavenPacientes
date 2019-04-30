/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tpatlate file, choose Tools | Tpatlates
 * and open the tpatlate in the editor.
 */
package com.dekses.jersey.docker.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author white
 */
public class PatientDAO {
    private static final Map<String, Patient> patMap = new HashMap<String, Patient>();
 
    static {
        initEmps();
    }
 
    private static void initEmps() {
        Patient pat1 = new Patient("E01", "Smith", "Clerk", "hoy", "micasa", "123", "9874656", "privado");
        Patient pat2 = new Patient("E02", "Leo", "Messi", "Sipatre", "Barcelona", "10", "g04t", "publico");
        Patient pat3 = new Patient("E03", "Jones", "Jhonas", "algun dia", "sucasa", "666", "666999", "privado");
 
        patMap.put(pat1.getId(), pat1);
        patMap.put(pat2.getId(), pat2);
        patMap.put(pat3.getId(), pat3);
    }
 
    public static Patient getPatient(String patNo) {
        return patMap.get(patNo);
    }
 
    public static Patient addPatient(Patient pat) {
        patMap.put(pat.getId(), pat);
        return pat;
    }
 
    public static Patient updatePatient(Patient pat) {
        patMap.put(pat.getId(), pat);
        return pat;
    }
 
    public static void deletePatient(String patNo) {
        patMap.remove(patNo);
    }
 
    public static List<Patient> getAllPatients() {
        Collection<Patient> c = patMap.values();
        List<Patient> list = new ArrayList<Patient>();
        list.addAll(c);
        return list;
    }
     
    List<Patient> list;
}
