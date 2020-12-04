package no.alexander.AdventOfCode1;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdventOfCode3Application implements CommandLineRunner {
	private static Logger LOG = LoggerFactory.getLogger(AdventOfCode3Application.class);
	private static final char TREE = '#';

	private static final int MAX_X = 31;
	
	public static void main(String[] args) {
		SpringApplication.run(AdventOfCode3Application.class, args);
	}

	@Override
	public void run(String... args) throws URISyntaxException, IOException {
		URL input = getClass().getClassLoader().getResource("input.txt");
		File file = new File(input.toURI());
		
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		
		List<Line> map = new ArrayList<>();
		for (String line : lines ) {
			map.add(new Line(line));
		}
		
		partOne(map);
		partTwo(map);
	}
	
	private void partOne(List<Line> lines) {
		var collisions = collisionForSpeed(3, 1, lines);
		LOG.info("Part one - " + collisions);
	}
	
	
	
	private void partTwo(List<Line> lines) {
		var collisionProduct = collisionForSpeed(1, 1, lines);
		collisionProduct = collisionProduct.multiply(collisionForSpeed(3, 1, lines));
		collisionProduct = collisionProduct.multiply(collisionForSpeed(5, 1, lines));
		collisionProduct = collisionProduct.multiply(collisionForSpeed(7, 1, lines));
		collisionProduct = collisionProduct.multiply(collisionForSpeed(1, 2, lines));
				
		LOG.info("Part two - " + collisionProduct);
	}
	
	private BigInteger collisionForSpeed(int x, int y, List<Line> lines) {
		var collisions = 0;
		Toboggan t = new Toboggan(x, y);
		while (t.y <= lines.size()) {
			t.move();
			if (t.y < lines.size()) {
				if ( lines.get(t.y).trees.get(t.x)) {
					collisions++;
				}
			}
		}
		
		return BigInteger.valueOf(collisions);
	}
	
	private class Line {
		private List<Boolean> trees;
		
		public Line(String input) {
			trees = new ArrayList<>();
			
			for (char c : input.toCharArray()) {
				trees.add(c == TREE);
			}
		}
	}
	
	private class Toboggan {
		private int deltaX;
		private int deltaY;
		
		private int x;
		private int y;
		
		public Toboggan(int deltaX, int deltaY) {
			this.deltaX = deltaX;
			this.deltaY = deltaY;
			this.x = 0;
			this.y = 0;
		}
		
		public void move() {
			x += deltaX;
			x = x % MAX_X;
			y += deltaY;
		}
	}
}
