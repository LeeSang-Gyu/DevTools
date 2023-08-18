package com.ssteam.nolcam.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssteam.nolcam.Interface.ViewPositionEvent;

import org.jetbrains.annotations.NotNull;

public class CirclePagerIndicatorDecoration extends RecyclerView.ItemDecoration {
    private int colorActive = 0xDE000000;
    private int colorInactive = 0x33000000;

    private static final float DP = Resources.getSystem().getDisplayMetrics().density;

//    ViewPositionEvent viewPositionEvent;
//
//
//    public void setMsg(ViewPositionEvent viewPositionEvent) {
//        this.viewPositionEvent = viewPositionEvent;
//    }


    /**
     * 뷰 하단에서 표시기가 차지하는 공간의  높이
     */
    private final int mIndicatorHeight = (int) (DP * 16);

    /**
     * 표시기 획 너비
     */
    private final float mIndicatorStrokeWidth = DP * 4;

    /**
     * 표시기 너비
     */
    private final float mIndicatorItemLength = DP * 4;
    /**
     * 표시기 사이의 패딩
     */
    private final float mIndicatorItemPadding = DP * 8;

    /**
     * 좀 더 자연스러운 에니메이션
     */
    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    private final Paint mPaint = new Paint();

    public CirclePagerIndicatorDecoration() {

        mPaint.setStrokeWidth(mIndicatorStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = parent.getAdapter().getItemCount();
        final int[] lastPosition = {0};

        // 가로로 가운데에 놓고 너비를 계산하고 가운데에서 절반을 뺌
        float totalLength = mIndicatorItemLength * itemCount;
        float paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding;
        float indicatorTotalWidth = totalLength + paddingBetweenItems;
        float indicatorStartX = (parent.getWidth() - indicatorTotalWidth) / 2F;

        // 할당된 공간에서 세로 중앙
        float indicatorPosY = parent.getHeight() - mIndicatorHeight / 2F;

        drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount);

        // 활성 페이지 찾기(강조 표시되어야 함)
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        int activePosition = layoutManager.findFirstVisibleItemPosition(); // 활성화된(보고있는) 위치

//        viewPositionEvent.setViewPosition(activePosition);//MapFragment로 콜백 (보고있는 위치)

        if (activePosition == RecyclerView.NO_POSITION) {
            return;
        }

        final View activeChild;

        parent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                if (lastVisibleItemPosition == itemTotalCount) {
                }
            }
        });


        // 활성 페이지의 오프셋 찾기(사용자가 스크롤하는 경우)

        if (activePosition == lastPosition[0]){
            activeChild = layoutManager.findViewByPosition(lastPosition[0]);
        }else{
             activeChild = layoutManager.findViewByPosition(activePosition);
        }
        int left = activeChild.getLeft();
        int width = activeChild.getWidth();
        int right = activeChild.getRight();

        // 스와이프할 때 활성 항목이 [-width, 0]에서 배치됩니다.
        // 부드러운 애니메이션을 위한 보간 오프셋
        float progress = mInterpolator.getInterpolation(left * -1/ (float) width);

        drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress);

    }

    private void drawInactiveIndicators(Canvas c, float indicatorStartX, float indicatorPosY, int itemCount) {
        mPaint.setColor(colorInactive);

        // 패딩을 포함한 항목 표시기의 너비
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        float start = indicatorStartX;
        for (int i = 0; i < itemCount; i++) {

            c.drawCircle(start, indicatorPosY, mIndicatorItemLength / 2F, mPaint);

            start += itemWidth;
        }
    }

    private void drawHighlights(Canvas c, float indicatorStartX, float indicatorPosY,
                                int highlightPosition, float progress) {
        mPaint.setColor(colorActive);

        // 패딩을 포함한 항목 표시기의 너비
        final float itemWidth = mIndicatorItemLength + mIndicatorItemPadding;

        if (progress == 0F) {
            // 스와이프 없음, 일반 표시기를 그립니다.
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;

            c.drawCircle(highlightStart, indicatorPosY, mIndicatorItemLength / 2F, mPaint);

        } else {
            float highlightStart = indicatorStartX + itemWidth * highlightPosition;
            // 부분 하이라이트 계산
            float partialLength = mIndicatorItemLength * progress + mIndicatorItemPadding * progress;

            c.drawCircle(highlightStart + partialLength, indicatorPosY, mIndicatorItemLength / 2F, mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = mIndicatorHeight;
    }
}