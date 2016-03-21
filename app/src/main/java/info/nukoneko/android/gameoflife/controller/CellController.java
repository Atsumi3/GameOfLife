package info.nukoneko.android.gameoflife.controller;import android.graphics.Canvas;import android.graphics.Paint;import android.graphics.Rect;import android.support.v7.app.AppCompatActivity;import android.view.Display;import android.view.MotionEvent;import info.nukoneko.android.gameoflife.BaseController;import info.nukoneko.android.gameoflife.MainSurface;/** * Created by TEJNEK on 2016/03/21. */public class CellController extends BaseController {    final int tick = 10;    final int margin = 0;    public final int rowNum = 50;    public Cell[][] cells;    int cellSize = 0;    private Paint _line = new Paint();    int count = 0;    public CellController(MainSurface holder) {        super(holder);        init();        cells[1][1].setLive(true);        cells[2][2].setLive(true);        cells[3][3].setLive(true);        cells[4][2].setLive(true);        cells[5][1].setLive(true);        newArg();    }    public void init(){        System.out.println("Cell Initialize!");        cells = new Cell[rowNum][rowNum];        Display disp = ((AppCompatActivity)holder.getContext()).getWindowManager().getDefaultDisplay();        int size = (disp.getHeight() > disp.getWidth()) ? disp.getWidth() : disp.getHeight();        final int maxCellSize = size / rowNum;        cellSize = maxCellSize - margin;        final int relativeMargin = (maxCellSize - cellSize) / 2;        _line.setARGB(255, 127, 255, 127);        _line.setAntiAlias(true);        for (int i = 0 ; i < rowNum; i++){            for (int j = 0 ; j < rowNum; j++) {                int maxLeft = i + ( i * maxCellSize);                int maxRight = maxLeft + maxCellSize;                int maxTop = j + (j * maxCellSize);                int maxBottom = maxTop + maxCellSize;                cells[i][j] = new Cell(this, new Rect(                        maxLeft + relativeMargin,                        maxTop + relativeMargin,                        maxRight - relativeMargin,                        maxBottom - relativeMargin),                        i,                        j);            }        }    }    public void newArg(){        Cell[][] tmp = cells;        for (int i = 0; i < cells.length; i++) {            for (int j = 0; j < cells.length; j++) {                tmp[j][i].getLiveCellCount();            }        }        for (int i = 0; i < cells.length; i++) {            for (int j = 0; j < cells.length; j++) {                Cell c = tmp[j][i];                if (c.isLive()){                    switch (c.livingCount){                        case 2:                        case 3:                            c.setLive(true);                            break;                        default:                            c.setLive(false);                    }                }else {                    c.setLive(c.livingCount == 3);                }            }        }        cells = tmp;    }    @Override    public void onDraw(Canvas c) {        for (int i = 0; i < cells.length; i++){            for (int j = 0 ; j < cells.length; j++){                Cell cell = cells[i][j];                if( cell.isLive()) {                    c.drawRect(cell.rect, _line);                }            }        }    }    @Override    public boolean onUpdate() {        if (count % tick == 0) {            newArg();        }        count++;        return super.onUpdate();    }    @Override    public boolean onTouchEvent(MotionEvent event) {        final int touchX = (int)event.getX() / cellSize;        final int touchY = (int)event.getY() / cellSize;        try {            cells[touchX][touchY].setLive(true);        } catch (Exception ignore){        }        return super.onTouchEvent(event);    }}