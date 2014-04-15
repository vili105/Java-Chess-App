/* Chess
 * Copyright (C) 2010-2014 Scott Weldon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class Bishop extends Piece {
  public Bishop() {
    super();
  }
  
  public Bishop(int x, int y, int width) {
    super(x, y, width);
    super.setType(Piece.BISHOP);
  }
  
  public Bishop(int x, int y, int width, int team) {
    super(x, y, width, team);
    super.setType(Piece.BISHOP);
  }
  
  public boolean getValidSquares(int oldX, int oldY, int newX, int newY,
      Team team, Piece[][] board) {
    int dx = newX - oldX;
    int dy = newY - oldY;
    
    if ((dx == dy) || (dx == -dy)) {
      if ((dx > 0) && (dy > 0)) {
          for (int i = 1; i <= dx; i++) {
            if (board[oldX + i - 1][oldY + i - 1] != null) {
              return false;
            }
          }
          return true;
      }
      else if ((dx > 0) && (dy < 0)) {
        for (int i = 1; i <= dx; i++) {
          if (board[oldX + i - 1][oldY - i - 1] != null) {
            return false;
          }
        }
        return true;
      }
      else if ((dx < 0) && (dy > 0)) {
        for (int i = -1; i >= dx; i--) {
          if (board[oldX + i - 1][oldY - i - 1] != null) {
            return false;
          }
        }
        return true;
      }
      else {
        for (int i = -1; i >= dx; i--) {
          if (board[oldX + i - 1][oldY + i - 1] != null) {
            return false;
          }
        }
        return true;
      }
    }
    else {
      return false;
    }
  }
  
  public boolean getAttackSquares(int oldX, int oldY, int newX, int newY,
      Team team, Piece[][] board) {
    int dx = newX - oldX;
    int dy = newY - oldY;
    int a, b = 0;
    
    if (dx > 0) {
      a = newX - 1;
    }
    else {
      a = newX + 1;
    }
    
    if (dy > 0) {
      b = newY - 1;
    }
    else {
      b = newY + 1;
    }
    
    if (this.getValidSquares(oldX, oldY, a, b, null, board)
        && (board[newX - 1][newY - 1] != null)
        && (board[newX - 1][newY - 1].getTeam() != team.color)) {
      return true;
    }
    
    return false;
  }
}
