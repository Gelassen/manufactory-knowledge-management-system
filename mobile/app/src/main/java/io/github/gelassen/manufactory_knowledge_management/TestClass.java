package io.github.gelassen.manufactory_knowledge_management;

import com.google.mlkit.vision.barcode.ZoomSuggestionOptions;

public class TestClass {

        private ZoomSuggestionOptions.ZoomCallback zoomCallback = new ZoomSuggestionOptions.ZoomCallback() {
            @Override
            public boolean setZoom(float zoomValue) {
                return false;
            }
        };
}
