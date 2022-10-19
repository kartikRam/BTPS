package com.example.btps1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProgramViewHolder_StudentList {
    ImageView itemImage;
    TextView Student_email;
    ImageView remove;
    TextView Student_enrollment;

    ProgramViewHolder_StudentList(View v){
        itemImage=v.findViewById(R.id.student_image);
        Student_email=v.findViewById(R.id.student_email);
        Student_enrollment=v.findViewById(R.id.student_enrollment);
        remove=v.findViewById(R.id.remove);
    }
}
