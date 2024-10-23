
package com.itsmcodez.iconstudio;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.itsmcodez.iconstudio.databinding.ActivityMainBinding;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String[] iconNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate and get instance of binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // set content view to binding's root
        setContentView(binding.getRoot());
        
        // retrieve icon names
        try {
            iconNames = getAssets().list("icon-packs/material-icons/");
        } catch(IOException err) { err.printStackTrace(); }
        
        binding.textField.setOnEditorActionListener((textView, actionId, keyEvent) -> {
                boolean actionHandled = false;
                if(actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(!TextUtils.isEmpty(textView.getText())) {
                    	// TODO: match input to names of items in recyclerview
                    }
                    // hide soft keyboard on input submission
                    InputMethodManager imm = (InputMethodManager) getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(binding.textField.getWindowToken(), 0);
                	actionHandled = true;
                }
                return actionHandled;
        });
        
        binding.textField.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    //TODO: implement this method
                }
                
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO: Implement this method
                }
                
                @Override
                public void afterTextChanged(Editable s) {
                    String input = s.toString().toLowerCase();
                    List<String> matchCases = new ArrayList<>();
                    for(String iconName : iconNames) {
                    	if(iconName.contains(input)) {
                    		matchCases.add(iconName);
                    	}
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, matchCases);
                    binding.textField.setAdapter(arrayAdapter);
                }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
