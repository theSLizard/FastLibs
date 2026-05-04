package org.theSLizard;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class UniversalSerialMemory<T> {

    private Cell beginning;
    private Cell end;

    private Integer size;

    public Integer getLowerIndex() {
        return lowerIndex;
    }

    public Integer getUpperIndex() {
        return upperIndex;
    }

    public Integer getSize() {
        return size;
    }

    private Integer lowerIndex;
    private Integer upperIndex;

    public static final class Cell<T> {

        private T data;
        private Integer index = 0;

        public Cell previous;
        public Cell next;

        public Cell(T data) {
           this.data = data;
        }
    }

    public UniversalSerialMemory() {
        this.beginning = null; this.end = null; this.lowerIndex = 0; this.upperIndex = 0;
    }


    public T dequeue() {
        if (null == this.end) {
            return null;
        } else {

            T removed = (T) this.end.data;

            if (null == this.end.previous) {
                this.beginning = null;
                this.end = null;
            } else {
                if (null != this.end.previous.next) {
                    this.end.previous.next = null;
                    this.end = this.end.previous;
                }
            }

            this.size--;
            this.upperIndex--;

            return removed;
        }
    }

    public void enqueue(T data) {
        this.appendCell(new Cell(data));
    }

    public void appendCell(Cell data) {
        if (null == this.end) {
            data.index = 0;
            this.beginning = data;
            this.end = data;
            this.size = 1;
        } else {
            data.index = this.end.index + 1;
            this.upperIndex = data.index;
            data.previous = this.end;
            this.end.next = data;
            this.end = data;
            this.size++;
        }
    }

    public void prependCell(Cell data) {
        if (null == this.beginning) {
            data.index = 0;
            this.beginning = data;
            this.end = data;
            this.size = 1;
        } else {
            data.index = this.beginning.index - 1;
            this.lowerIndex = data.index;
            data.next = this.beginning;
            this.beginning.previous = data;
            this.beginning = data;
            this.size++;

        }
    }

    public List<T> dumpCells() {
        Cell cell = this.beginning;
        List<T> cells = new ArrayList<T>();

        if (null != cell) {
            while(null != cell) {
                cells.add((T) cell.data);
                cell = cell.next;
            }
        }
        return cells;
    }

    private Cell<T> seekMemory(Integer location) {
        Cell cell = null;

        if (location + abs(lowerIndex) > size / 2){
            cell = this.end;
            while(cell.index > location) {
                cell = cell.previous;
            }
        } else {
            cell = this.beginning;
            while(cell.index < location) {
                cell = cell.next;
            }
        }

        return cell;
    }

    public <T extends Comparable<T>> boolean priorityWedgeMemory(T data) {

        Cell cell = this.beginning;

        // find position
        if (null != cell) {
            while((null != cell) && (data.compareTo((T) cell.data) >= 0)) {
                    cell = cell.next;
                }
            }

        // insert into priority queue
        if (null != cell) {
            this.upperIndex ++;
            this.size ++;

            Cell newCell = new Cell(data);

            newCell.previous = cell.previous;
            newCell.next = cell;

            cell.previous = newCell;
            cell.previous.previous.next = newCell;

            // assign a correct index
            newCell.index = cell.previous.previous.index + 1;
            cell.index = newCell.index;

            // rebuild indexes after cell (the one *after* the inserted cell)
            while(null != cell) {
                cell.index++;
                cell = cell.next;
            }

            // item inserted - index used as "priority" indicator for the queue
            return true;
        }

        // no item inserted
        return false;
    }

    public boolean wedgeMemory(Integer location, T data) {
        if (location < this.lowerIndex || location > upperIndex) {
            return false;
        } else {
            Cell cellAtLocation = this.seekMemory(location);
            Cell nextCellAtLocation = cellAtLocation.next;

            Cell newCell = new Cell(data);
            newCell.index = cellAtLocation.index + 1;
            newCell.previous = cellAtLocation;
            newCell.next = nextCellAtLocation;
            cellAtLocation.next = newCell;

            Cell cell = nextCellAtLocation;
            if (null != cell) {
                cell.previous = newCell;
                while(null != cell) {
                    cell.index += 1;
                    cell = cell.next;
                }
            }

            this.upperIndex ++;
            this.size ++;
            return true;
        }
    }

    public boolean writeMemory(Integer location, T data) {
        if (location < this.lowerIndex || location > upperIndex) {
            return false;
        } else {
            this.seekMemory(location).data = data;
            return true;
        }
    }

    public T readMemory(Integer location) {
        if (location < this.lowerIndex || location > upperIndex) {
            return null;
        } else {
            return (T)this.seekMemory(location).data;
        }
    }
}
