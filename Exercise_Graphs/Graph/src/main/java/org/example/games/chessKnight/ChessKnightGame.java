package org.example.games.chessKnight;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessKnightGame {

    private final int sizeX, sizeY;
    private final int board[];
    private final Point deltas[] = {
            new Point(-1, 2),
            new Point(1, 2),
            new Point(-1, -2),
            new Point(1, -2),
            new Point(-2, 1),
            new Point(2, 1),
            new Point(-2, -1),
            new Point(2, -1),
    };

    public ChessKnightGame(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.board = new int[sizeX * sizeY];
    }

    public int get(int x, int y) {
        return board[x + y * sizeX];
    }

    private void set(int x, int y, int n) {
        board[x + y * sizeX] = n;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY;
    }

    private java.util.List<Point> computeTargets(int x, int y) {
        java.util.List<Point> targets = new ArrayList<Point>();
        for (Point delta : deltas) {
            int tx = x + delta.x;
            int ty = y + delta.y;
            if (isValid(tx, ty) && get(tx, ty) == 0) {
                targets.add(new Point(tx, ty));
            }
        }
        return targets;
    }

    public boolean solve(int currentX, int currentY, int step) {
        if (step == sizeX * sizeY) {
            set(currentX, currentY, step);
            return true;
        }
        set(currentX, currentY, step);

        java.util.List<Point> targets = computeTargets(currentX, currentY);

        applyWarnsdorffRule(targets);

        for (Point target : targets) {
            boolean solved = solve(target.x, target.y, step + 1);
            if (solved) {
                return true;
            }
        }
        set(currentX, currentY, 0);

        return false;
    }

    private void applyWarnsdorffRule(java.util.List<Point> targets) {
        Map<Point, Integer> targetCounters = new HashMap<Point, Integer>();
        for (Point target : targets) {
            List<Point> nextTargets = computeTargets(target.x, target.y);
            targetCounters.put(target, nextTargets.size());
        }
        targets.sort(new Comparator<Point>() {
            @Override
            public int compare(Point p0, Point p1) {
                Integer c0 = targetCounters.get(p0);
                Integer c1 = targetCounters.get(p1);
                return Integer.compare(c0, c1);
            }
        });
    }

}
