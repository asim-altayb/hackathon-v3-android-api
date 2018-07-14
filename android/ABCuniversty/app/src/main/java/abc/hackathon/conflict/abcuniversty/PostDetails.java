package abc.hackathon.conflict.abcuniversty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class PostDetails extends AppCompatActivity {
    TextView postTitle,postDate,documentName,postContent;
    EditText comment;
    Button  commentSend;
    ImageButton documentDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        postTitle    = (TextView) findViewById(R.id.post_title);
        postDate     = (TextView) findViewById(R.id.post_date);
        documentName = (TextView) findViewById(R.id.document_name);
        postContent  = (TextView) findViewById(R.id.content);

        comment          = (EditText) findViewById(R.id.comment);

        documentDownload = (ImageButton) findViewById(R.id.document_download);
        commentSend      = (Button) findViewById(R.id.send_comment);

    }
}
