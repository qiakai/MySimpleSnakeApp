package com.androidstudy.myapp.view.startmain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class SnakeView extends View {
    public static final String TAG = "SnakeView";

    private int mWidth;
    private int mHeight;
    private static final int sXOffset = 0;
    private static final int sYOffset = 0;

    private final int BOXWIDTH = 30;
    private Random mRandom = new Random();
    private Point mFoodPosition;
    private boolean mIsFoodDone = true;
    private boolean mIsBoxDone = true;
    private Point mBoxPosition;

    private boolean mIsDeath = false;
    private boolean mIsEat = false;

    private ArrayList<Point> mSnakeList;
    private Paint mSnakePaint;
    private int mSnakeDirection = 0;
    private final int UP = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private final int RIGHT = 4;

    private Paint mBgPaint;
    private Paint mFoodPaint;
    private Paint mBoxPaint;

    private int fenshu = 0;

    public SnakeView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SnakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public SnakeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        // TODO Auto-generated constructor stub
        mSnakeList = new ArrayList<Point>();
        mSnakePaint = new Paint();
        mSnakePaint.setColor(Color.RED);
        mSnakePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mSnakeList.add(new Point(500, 500));
        mSnakeList.add(new Point(500, 530));
        mSnakeList.add(new Point(500, 560));
        mSnakeList.add(new Point(500, 590));
        mSnakeList.add(new Point(500, 620));

        mSnakeDirection = RIGHT;//蛇方向
        mIsFoodDone = true;
        mFoodPosition = new Point();//食物位置

        mBoxPosition = new Point();//陷阱位置
        mIsBoxDone = true;

        mFoodPaint = new Paint();
        mFoodPaint.setColor(Color.CYAN);//食物颜色
        mFoodPaint.setStyle(Paint.Style.FILL);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(Color.YELLOW);//陷阱颜色
        mBoxPaint.setStyle(Paint.Style.FILL);

        mBgPaint = new Paint();
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) (event.getX());
        int y = (int) (event.getY());
        Log.e(TAG, "x =" + x + " y = " + y + " mSnakeDirection = " + mSnakeDirection);

        Point head = mSnakeList.get(0);
        Log.e(TAG, "head.x = " + head.x + " head.y = " + head.y);
        if (mSnakeDirection == UP || mSnakeDirection == DOWN) {
            if (x < head.x) mSnakeDirection = LEFT;
            if (x > head.x) mSnakeDirection = RIGHT;
        } else if (mSnakeDirection == LEFT || mSnakeDirection == RIGHT) {
            if (y < head.y) mSnakeDirection = UP;
            if (y > head.y) mSnakeDirection = DOWN;
        }
        //Log.e(TAG, "after adjust mSnakeDirection = " + mSnakeDirection);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas, mBgPaint);//画背景
        drawFood(canvas, mFoodPaint);//画食物
//        drawBox(canvas, mBoxPaint);
        drawSnake(canvas, mSnakePaint);//画蛇

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    //画背景
    private void drawBg(Canvas canvas, Paint paint) {
//        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawColor(Color.WHITE);
    }

    //画蛇
    private void drawSnake(Canvas canvas, Paint paint) {
        for (int i = 0; i < mSnakeList.size(); i++) {
            if (i == 0) {//蛇头部为黑色
                Point headpoint = mSnakeList.get(i);
                Rect recth = new Rect(headpoint.x, headpoint.y, headpoint.x + BOXWIDTH, headpoint.y + BOXWIDTH);
                Paint painth = new Paint();
                painth.setColor(Color.BLACK);
                painth.setStyle(Paint.Style.FILL_AND_STROKE);
                painth.setStrokeCap(Paint.Cap.ROUND);
                canvas.drawRect(recth, painth);
                continue;
            }
            Point point = mSnakeList.get(i);
            Rect rect = new Rect(point.x, point.y, point.x + BOXWIDTH, point.y + BOXWIDTH);
            canvas.drawRect(rect, paint);
        }
        snakeMove(mSnakeList, mSnakeDirection);
        if (isBoxEaten()) {
            mIsBoxDone = true;
        }
        if (isFoodEaten()) {
            mIsFoodDone = true;
            fenshu++;
            mIsEat = true;
        } else {
            mSnakeList.remove(mSnakeList.size() - 1);
        }
    }

    //画食物
    private void drawFood(Canvas canvas, Paint paint) {
        if (mIsFoodDone) {
            mFoodPosition.x = mRandom.nextInt(mWidth - BOXWIDTH) + sXOffset;
            mFoodPosition.y = mRandom.nextInt(mWidth - BOXWIDTH) + sYOffset;
            mIsFoodDone = false;
            mIsEat = false;
        }
        Rect food = new Rect(mFoodPosition.x, mFoodPosition.y, mFoodPosition.x + BOXWIDTH, mFoodPosition.y + BOXWIDTH);
        canvas.drawRect(food, paint);
    }

    //画陷阱
    private void drawBox(Canvas canvas, Paint paint) {
        if (mIsBoxDone) {
//            mBoxPosition.x = mRandom.nextInt(mWidth - BOXWIDTH) + sXOffset;
//            mBoxPosition.y = mRandom.nextInt(mWidth - BOXWIDTH) + sYOffset;
//            mIsBoxDone = false;
            mIsDeath = true;
        }
        Rect box = new Rect(mBoxPosition.x, mBoxPosition.y, mBoxPosition.x + BOXWIDTH, mBoxPosition.y + BOXWIDTH);
        canvas.drawRect(box, paint);
    }

    //蛇移动
    public void snakeMove(ArrayList<Point> list, int direction) {
        Point orighead = list.get(0);
        Point newhead = new Point();

        switch (direction) {
            case UP:
                newhead.x = orighead.x;
                newhead.y = orighead.y - BOXWIDTH;
                break;
            case DOWN:
                newhead.x = orighead.x;
                newhead.y = orighead.y + BOXWIDTH;
                break;
            case LEFT:
                newhead.x = orighead.x - BOXWIDTH;
                newhead.y = orighead.y;
                break;
            case RIGHT:
                newhead.x = orighead.x + BOXWIDTH;
                newhead.y = orighead.y;
                break;
            default:
                break;
        }
        adjustHead(newhead);
        list.add(0, newhead);
    }

    //是否出界
    private boolean isOutBound(Point point) {
        if (point.x < sXOffset || point.x > mWidth - sXOffset) return true;
        return point.y < sYOffset || point.y > mHeight - sYOffset;
    }

    //出界调整头部，转换方向
    private void adjustHead(Point point) {
        if (isOutBound(point)) {
            if (mSnakeDirection == UP) point.y = mHeight - sYOffset - BOXWIDTH;
            if (mSnakeDirection == DOWN) point.y = sYOffset;
            if (mSnakeDirection == LEFT) point.x = mWidth - sYOffset - BOXWIDTH;
            if (mSnakeDirection == RIGHT) point.x = sXOffset;
        }
    }

    //是否吃到食物
    private boolean isFoodEaten() {
        if (!mIsFoodDone) {
            Rect foodrect = new Rect(mFoodPosition.x, mFoodPosition.y, mFoodPosition.x + BOXWIDTH, mFoodPosition.y + BOXWIDTH);
            Point head = mSnakeList.get(0);
            Rect headrect = new Rect(head.x, head.y, head.x + BOXWIDTH, head.y + BOXWIDTH);
            return foodrect.intersect(headrect);
        }
        return false;
    }

    //是否撞到陷阱
    private boolean isBoxEaten() {
        if (!mIsBoxDone) {
            Rect boxrect = new Rect(mBoxPosition.x, mBoxPosition.y, mBoxPosition.x + BOXWIDTH, mBoxPosition.y + BOXWIDTH);
            Point head = mSnakeList.get(0);
            Rect headrect = new Rect(head.x, head.y, head.x + BOXWIDTH, head.y + BOXWIDTH);
            return boxrect.intersect(headrect);
        }
        return false;
    }

    public void changeSnake(int i) {
        mSnakeDirection = i;
    }

    public boolean isDeath() {
        return mIsDeath;
    }

    public boolean isEat() {
        return mIsEat;
    }

    public int getfenshu() {
        return fenshu;
    }
}
