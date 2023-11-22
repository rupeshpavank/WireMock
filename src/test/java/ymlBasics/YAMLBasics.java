package ymlBasics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

public class YAMLBasics {

	public static void main(String[] args) throws FileNotFoundException {
		
		Yaml yml=new Yaml();
		
		String path="C:\\Users\\kittu\\eclipse-workspace\\WireMockPractice\\src\\test\\java\\ymlBasics\\basicYml.yml";
		
		Map<String, List<List<String>>> data=	yml.load(new FileInputStream(new File(path)));

		System.out.println("First Array Group:");
	    data.values()
	            .stream()
	            .collect(Collectors.toList())
	            .get(0)
	            .get(0)
	            .forEach(System.out::println);
	    
	    
	 // Extract second sublist
	    System.out.println("\nSecond Array Group:");
	    data.values()
	            .stream()
	            .collect(Collectors.toList())
	            .get(0)
	            .get(1)
	            .forEach(System.out::println);
		
		    
		}

		
		
	  

		
		
		  }  

		   

			
	
		
		
		
	



