package tutorialwing.com.ottolibrarytutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addFragment();
	}

	@Override
	protected void onStart() {
		super.onStart();
		GlobalBus.getBus().register(this);
	}

	private void addFragment() {
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new UserFragment()).commit();
	}

	public void sendMessageToFragment(View view) {
		EditText etMessage = (EditText) findViewById(R.id.activityData);
		Events.Message2 activityFragmentMessageEvent =
				new Events.Message2(String.valueOf(etMessage.getText()));
		GlobalBus.getBus().post(activityFragmentMessageEvent);
	}

	@Subscribe
	public void getMessagez(Events.Message1 message1) {
		TextView messageView = (TextView) findViewById(R.id.message);
		messageView.setText(getString(R.string.message_received) + " " + message1.getMessage());

		Toast.makeText(getApplicationContext(),
				getString(R.string.message_main_activity) + " " + message1.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	@Produce
	public Events.Message2 produceEvent() {
		// Assuming that we are tracking the last messages for this
		// event (i.e. FragmentActivityMessage) and the message is "Hello Tutorialwing"
		return new Events.Message2("Hello Tutorialwing");
	}

	@Override
	protected void onStop() {
		super.onStop();
		GlobalBus.getBus().unregister(this);
	}
}
