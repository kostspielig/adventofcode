package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

func main() {
	data, err := ioutil.ReadFile("input.txt")
	if err != nil {
		fmt.Println("Cannot read input.txt file")
		panic(err)
	}

	fmt.Printf("Task 1: %d \n", task1(string(data)))
	fmt.Printf("Task 2: %s \n", task2(string(data)))
}

func find2or3duplicates(input string) (bool, bool) {
	counts := make(map[string]int)
	two := false
	three := false
	for _, c := range input {
		counts[string(c)]++
	}

	for _, n := range counts {
		if n == 2 {
			two = true
		} else if n == 3 {
			three = true
		}
	}

	return two, three
}

func task1(input string) int {
	twos, threes := 0, 0
	for _, line := range strings.Split(string(input), "\n") {
		two, three := find2or3duplicates(line)
		if two {
			twos++
		}
		if three {
			threes++
		}
	}

	return twos * threes
}

func getCommon(s1 string, s2 string) string {
	if len(s1) != len(s2) {
		return ""
	}

	res := ""
	for i, c := range s1 {
		if c == rune(s2[i]) {
			res += string(c)
		}
	}

	return res
}

func task2(input string) string {
	lines := strings.Split(string(input), "\n")

	for i, line1 := range lines {
		for _, line2 := range lines[i:len(lines)] {
			common := getCommon(line1, line2)
			if len(common) == len(line1)-1 {
				return common
			}
		}
	}

	return ""
}
