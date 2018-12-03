use std::fs::File;
use std::io::{BufRead, BufReader};
use regex::Regex;

extern crate regex;

fn main() {
    let file = File::open("input.txt").expect("File not found");

    let mut claims: Vec<Claim> = Vec::new();
    let file = BufReader::new(file);
    for line in file.lines().filter_map(|result| result.ok()) {
        let re = Regex::new(r"#(\d{1,4}) @ (\d{1,3}),(\d{1,3}): (\d{1,2})x(\d{1,2})").unwrap();
        for cap in re.captures_iter(&line) {
            claims.push(build_claim(&cap[1], &cap[2], &cap[3], &cap[4], &cap[5]));
        }
    }

    println!("Task 1: {}", task1(&claims));
    println!("Task 2: {}", task2(&claims));
}

// Use Degug formatting to print a Claim {:?}
#[derive(Debug)]
struct Claim {
    id: i32,
    left: i32,
    top: i32,
    width: i32,
    height: i32
}


fn build_claim(id: &str, left: &str, top: &str, width: &str, height: &str) -> Claim {
    Claim {
        id: id.parse::<i32>().unwrap(),
        left: left.parse::<i32>().unwrap(),
        top: top.parse::<i32>().unwrap(),
        width: width.parse::<i32>().unwrap(),
        height: height.parse::<i32>().unwrap()
    }
}

fn task1(input: &Vec<Claim>) -> String {
    let mut _board: [[i32; 1000]; 1000] = [[0; 1000]; 1000];

    for claim in input {
        for i in claim.top..(claim.top+claim.height) {
            for j in claim.left..(claim.left+claim.width) {
                _board[i as usize][j as usize] = _board[i as usize][j as usize] + 1;
            }
        }
    }

    _board.iter()
        .flat_map(|r| r.iter())
        .fold(0, |acc, e| if *e > 1 { acc + 1 } else { acc })
        .to_string()
}


fn task2(input: &Vec<Claim>) -> String {
    let mut _board: [[i32; 1000]; 1000] = [[0; 1000]; 1000];
    for claim in input {
        for i in claim.top..(claim.top+claim.height) {
            for j in claim.left..(claim.left+claim.width) {
                let val = _board[i as usize][j as usize];
                if val == 0 {
                    _board[i as usize][j as usize] = claim.id;
                } else {
                    _board[i as usize][j as usize] = -1;
                }
            }
        }
    }

    let mut unique = -1;

    for claim in input {
        let mut overlap = false;
        'outer: for i in claim.top..(claim.top+claim.height) {
            for j in claim.left..(claim.left+claim.width) {
                let val = _board[i as usize][j as usize];
                if val != claim.id {
                    overlap = true;
                    continue 'outer;
                }
            }
        }

        if overlap == false {
            unique = claim.id;
        }
    }

    unique.to_string()
}
