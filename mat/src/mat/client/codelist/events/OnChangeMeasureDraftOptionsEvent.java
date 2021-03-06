package mat.client.codelist.events;



import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * The Class OnChangeMeasureDraftOptionsEvent.
 * 
 * @author aschmidt
 */
public class OnChangeMeasureDraftOptionsEvent extends GwtEvent<OnChangeMeasureDraftOptionsEvent.Handler> {
	
	/** The type. */
	public static GwtEvent.Type<OnChangeMeasureDraftOptionsEvent.Handler> TYPE = 
		new GwtEvent.Type<OnChangeMeasureDraftOptionsEvent.Handler>();
		
	/**
	 * The Interface Handler.
	 */
	public static interface Handler extends EventHandler {
		
		/**
		 * On change options.
		 * 
		 * @param event
		 *            the event
		 */
		public void onChangeOptions(OnChangeMeasureDraftOptionsEvent event);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
	 */
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared.EventHandler)
	 */
	@Override
	protected void dispatch(Handler handler) {
		handler.onChangeOptions(this);
	}
}
