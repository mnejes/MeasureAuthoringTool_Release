package mat.client.measure.metadata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.HelpBlock;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import mat.client.Mat;
import mat.client.MatPresenter;
import mat.client.MeasureComposerPresenter;
import mat.client.clause.clauseworkspace.model.MeasureDetailResult;
import mat.client.clause.clauseworkspace.model.MeasureXmlModel;
import mat.client.codelist.ListBoxCodeProvider;
import mat.client.event.BackToMeasureLibraryPage;
import mat.client.event.MeasureDeleteEvent;
import mat.client.event.MeasureEditEvent;
import mat.client.event.MeasureSelectedEvent;
import mat.client.measure.ManageMeasureDetailModel;
import mat.client.measure.ManageMeasureSearchModel;
import mat.client.measure.ManageMeasureSearchModel.Result;
import mat.client.measure.service.MeasureServiceAsync;
import mat.client.measure.service.SaveMeasureResult;
import mat.client.shared.DateBoxWithCalendar;
import mat.client.shared.GenericResult;
import mat.client.shared.HasVisible;
import mat.client.shared.ListBoxMVP;
import mat.client.shared.MatContext;
import mat.client.shared.MessageAlert;
import mat.client.shared.MessageDelegate;
import mat.client.shared.PrimaryButton;
import mat.client.shared.ReadOnlyHelper;
import mat.client.shared.SuccessMessageAlert;
import mat.client.shared.TextAreaWithMaxLength;
import mat.client.shared.WarningConfirmationMessageAlert;
import mat.client.shared.ui.DeleteConfirmDialogBox;
import mat.model.Author;
import mat.model.MeasureSteward;
import mat.model.MeasureType;
import mat.model.QualityDataSetDTO;
import mat.shared.ConstantMessages;
import mat.shared.MatConstants;
import mat.shared.StringUtility;

/**
 * The Class MetaDataPresenter.
 */
public class MetaDataPresenter  implements MatPresenter {
	
	/**
	 * The Interface MetaDataDetailDisplay.
	 */
	public static interface MetaDataDetailDisplay {
		
		
		/**
		 * Gets the short name.
		 * 
		 * @return the short name
		 */
		public TextBox getShortName();
		
		//US 421. Measure scoring choice is now part of measure creation process. So, this module just displays the choice.
		/**
		 * Gets the measure scoring.
		 * 
		 * @return the measure scoring
		 */
		public TextBox getMeasureScoring();
		
		
		/**
		 * Gets the focus panel.
		 * 
		 * @return the focus panel
		 */
		public HasKeyDownHandlers getFocusPanel();
		
		/**
		 * Gets the version number.
		 * 
		 * @return the version number
		 */
		public TextBox getVersionNumber();
		
		/**
		 * Gets the sets the name.
		 * 
		 * @return the sets the name
		 */
		public HasValue<String> getSetName();
		
		/**
		 * Gets the e measure identifier.
		 * 
		 * @return the e measure identifier
		 */
		public TextBox geteMeasureIdentifier();
		
		/**
		 * Gets the nqf id.
		 * 
		 * @return the nqf id
		 */
		public HasValue<String> getNqfId();
		
		/**
		 * Gets the finalized date.
		 * 
		 * @return the finalized date
		 */
		public TextBox getFinalizedDate();
		
		/**
		 * Gets the measurement from period.
		 * 
		 * @return the measurement from period
		 */
		public String getMeasurementFromPeriod();
		
		/**
		 * Gets the measurement from period input box.
		 * 
		 * @return the measurement from period input box
		 */
		public DateBoxWithCalendar getMeasurementFromPeriodInputBox();
		
		/**
		 * Gets the measurement to period.
		 * 
		 * @return the measurement to period
		 */
		public String getMeasurementToPeriod();
		
		/**
		 * Gets the measurement to period input box.
		 * 
		 * @return the measurement to period input box
		 */
		public DateBoxWithCalendar getMeasurementToPeriodInputBox();
		
		/**
		 * Gets the measure type.
		 * 
		 * @return the measure type
		 */
		public String getMeasureType();
		
		/**
		 * Gets the author.
		 * 
		 * @return the author
		 */
		public String getAuthor();
		
		/**
		 * Sets the authors list.
		 * 
		 * @param author
		 *            the new authors list
		 */
		public void setAuthorsSelectedList(List<Author> author);
		
		/**
		 * Gets the authors selected list.
		 *
		 * @return the authors selected list
		 */
		public List<Author> getAuthorsSelectedList();
		
		/**
		 * Gets the description.
		 * 
		 * @return the description
		 */
		public HasValue<String> getDescription();
		
		/**
		 * Gets the copyright.
		 * 
		 * @return the copyright
		 */
		public HasValue<String> getCopyright();
		
		/**
		 * Gets the clinical recommendation.
		 * 
		 * @return the clinical recommendation
		 */
		public HasValue<String> getClinicalRecommendation();
		
		/**
		 * Gets the definitions.
		 * 
		 * @return the definitions
		 */
		public HasValue<String> getDefinitions();
		
		/**
		 * Gets the guidance.
		 * 
		 * @return the guidance
		 */
		public HasValue<String> getGuidance();
		
		/**
		 * Gets the transmission format.
		 * 
		 * @return the transmission format
		 */
		public HasValue<String> getTransmissionFormat();
		
		/**
		 * Gets the rationale.
		 * 
		 * @return the rationale
		 */
		public HasValue<String> getRationale();
		
		/**
		 * Gets the improvement notation.
		 * 
		 * @return the improvement notation
		 */
		public HasValue<String> getImprovementNotation();
		
		/**
		 * Gets the stratification.
		 * 
		 * @return the stratification
		 */
		public HasValue<String> getStratification();
		
		/**
		 * Gets the risk adjustment.
		 * 
		 * @return the risk adjustment
		 */
		public HasValue<String> getRiskAdjustment();
		
		/**
		 * Gets the reference.
		 * 
		 * @return the reference
		 */
		public HasValue<String> getReference();
		
		/**
		 * Gets the supplemental data.
		 * 
		 * @return the supplemental data
		 */
		public HasValue<String> getSupplementalData();
		
		/**
		 * Gets the disclaimer.
		 * 
		 * @return the disclaimer
		 */
		public HasValue<String> getDisclaimer();
		
		/**
		 * Gets the initial patient pop.
		 * 
		 * @return the initial patient pop
		 */
		public HasValue<String> getInitialPop();
		
		/**
		 * Gets the denominator.
		 * 
		 * @return the denominator
		 */
		public HasValue<String> getDenominator();
		
		/**
		 * Gets the denominator exclusions.
		 * 
		 * @return the denominator exclusions
		 */
		public HasValue<String> getDenominatorExclusions();
		
		/**
		 * Gets the numerator.
		 * 
		 * @return the numerator
		 */
		public HasValue<String> getNumerator();
		
		/**
		 * Gets the numerator exclusions.
		 * 
		 * @return the numerator exclusions
		 */
		public HasValue<String> getNumeratorExclusions();
		
		/**
		 * Gets the denominator exceptions.
		 * 
		 * @return the denominator exceptions
		 */
		public HasValue<String> getDenominatorExceptions();
		
		/**
		 * Gets the measure population.
		 * 
		 * @return the measure population
		 */
		public HasValue<String> getMeasurePopulation();
		
		/**
		 * Gets the measure observations.
		 * 
		 * @return the measure observations
		 */
		public HasValue<String> getMeasureObservations();
		
		/**
		 * Gets the rate aggregation.
		 * 
		 * @return the rate aggregation
		 */
		public HasValue<String> getRateAggregation();
		
		/**
		 * Gets the emeasure id.
		 * 
		 * @return the emeasure id
		 */
		public HasValue<String> getEmeasureId();
		
		/**
		 * Gets the generate emeasure id button.
		 * 
		 * @return the generate emeasure id button
		 */
		public HasClickHandlers getGenerateEmeasureIdButton();
		
		/**
		 * Sets the generate emeasure id button enabled.
		 * 
		 * @param b
		 *            the new generate emeasure id button enabled
		 */
		public void setGenerateEmeasureIdButtonEnabled(boolean b);
		
		/**
		 * Gets the reference values.
		 * 
		 * @return the reference values
		 */
		public List<String> getReferenceValues();
		
		/**
		 * Sets the reference values.
		 * 
		 * @param values
		 *            the values
		 * @param editable
		 *            the editable
		 */
		public void setReferenceValues(List<String> values, boolean editable);
		
		/**
		 * Sets the adds the edit buttons visible.
		 * 
		 * @param b
		 *            the new adds the edit buttons visible
		 */
		public void setAddEditButtonsVisible(boolean b);
		
		/**
		 * Sets the save button enabled.
		 * 
		 * @param b
		 *            the new save button enabled
		 */
		public void setSaveButtonEnabled(boolean b);
		
		/**
		 * Gets the save button.
		 * 
		 * @return the save button
		 */
		public HasClickHandlers getSaveButtonHasClickHandlers();
		
		/**
		 * Gets the save btn.
		 * 
		 * @return the save btn
		 */
		public Button getBottomSaveButton();
		
		/**
		 * Gets the delete measure.
		 * 
		 * @return the delete measure
		 */
		public Button getBottomDeleteMeasureButton();
		
		/**
		 * Gets the measure population exclusions.
		 *
		 * @return the measure population exclusions
		 */
		HasValue<String> getMeasurePopulationExclusions();
		
		/**
		 * Builds the measure type cell table.
		 *
		 * @param measureTypeDTOList the measure type dto list
		 * @param isEditable the is editable
		 */
		public void buildMeasureTypeCellTable(List<MeasureType> measureTypeDTOList,
				boolean isEditable);
		
		/**
		 * Gets the qdm selected list.
		 *
		 * @return the qdm selected list
		 */
		public List<QualityDataSetDTO> getQdmSelectedList();
		
		/**
		 * Sets the qdm selected list.
		 *
		 * @param qdmSelectedList the new qdm selected list
		 */
		public void setQdmSelectedList(List<QualityDataSetDTO> qdmSelectedList);
		
		/**
		 * Gets the component measure selected list.
		 *
		 * @return the component measure selected list
		 */
		List<ManageMeasureSearchModel.Result> getComponentMeasureSelectedList();
		
		/**
		 * Sets the component measure selected list.
		 *
		 * @param componentMeasureSelectedList the new component measure selected list
		 */
		void setComponentMeasureSelectedList(List<ManageMeasureSearchModel.Result> componentMeasureSelectedList);
		
		/**
		 * Gets the search string.
		 *
		 * @return the search string
		 */
		HasValue<String> getSearchString();
		
		/**
		 * Gets the search button.
		 *
		 * @return the search button
		 */
		PrimaryButton getSearchButton();
		
		/**
		 * Gets the dialog box.
		 *
		 * @return the dialog box
		 */
		DialogBox getDialogBox();
		
		/**
		 * As component measures widget.
		 *
		 * @return the widget
		 */
		Widget asComponentMeasuresWidget();
		
		/**
		 * Builds the component measures selected list.
		 *
		 * @param result the result
		 * @param editable the editable
		 */
		void buildComponentMeasuresSelectedList(List<ManageMeasureSearchModel.Result> result, boolean editable);
		
		/**
		 * Gets the measure type selected list.
		 *
		 * @return the measure type selected list
		 */
		List<MeasureType> getMeasureTypeSelectedList();
		
		/**
		 * Sets the measure type selected list.
		 *
		 * @param measureTypeSelectedList the new measure type selected list
		 */
		void setMeasureTypeSelectedList(List<MeasureType> measureTypeSelectedList);
		
		/**
		 * Builds the author cell table.
		 *
		 * @param authorList the author list
		 * @param editable the editable
		 */
		public void buildAuthorCellTable(List<Author> authorList,
				boolean editable);
		
		/**
		 * Gets the save error msg.
		 *
		 * @return the save error msg
		 */
		WarningConfirmationMessageAlert getSaveErrorMsg();
		
		/**
		 * Gets the error message display.
		 *
		 * @return the error message display
		 */
		MessageAlert getBottomErrorMessage();
		
		/**
		 * As widget.
		 *
		 * @return the widget
		 */
		Widget asWidget();
		
		/**
		 * Gets the success message display.
		 *
		 * @return the success message display
		 */
		MessageAlert getBottomSuccessMessage();
		
		/**
		 * Sets the steward id.
		 *
		 * @param stewardId the new steward id
		 */
		public void setStewardId(String stewardId);
		
		/**
		 * Gets the steward id.
		 *
		 * @return the steward id
		 */
		String getStewardId();
		
		/**
		 * Gets the steward value.
		 *
		 * @return the steward value
		 */
		String getStewardValue();
		
		/**
		 * Sets the steward value.
		 *
		 * @param stewardValue the new steward value
		 */
		public void setStewardValue(String stewardValue);
		
		/**
		 * Gets the calender year.
		 *
		 * @return the calender year
		 */
		CheckBox getCalenderYear();
		
		/**
		 * Sets the measurement period buttons visible.
		 *
		 * @param b the new measurement period buttons visible
		 */
		void setMeasurementPeriodButtonsVisible(boolean b);
		
		void setMeasureScoringType(String measureScoringType);
		
		void buildForm();
		
		ListBox getAuthorListBox();
		
		ListBox getMeasureTypeListBox();
		
		Button getGenerateeMeasureIDButton();
		
		TextAreaWithMaxLength getTransmissionFormatInput();
		
		TextAreaWithMaxLength getDefinitionsInput();
		
		TextAreaWithMaxLength getReferenceInput();
		
		TextAreaWithMaxLength getImprovementNotationInput();
		
		TextAreaWithMaxLength getClinicalStmtInput();
		
		TextAreaWithMaxLength getSetNameInput();
		
		TextBox getNQFIDInput();
		
		TextBox getCodeSystemVersionInput();
		
		TextAreaWithMaxLength getSupplementalDataInput();
		
		DateBoxWithCalendar getMeasurePeriodToInput();
		
		DateBoxWithCalendar getMeasurePeriodFromInput();
		
		ListBoxMVP getMeasureTypeInput();
		
		TextAreaWithMaxLength getMeasureObservationsInput();
		
		TextAreaWithMaxLength getMeasurePopulationExclusionsInput();
		
		TextAreaWithMaxLength getMeasurePopulationInput();
		
		TextBox getAbbrInput();
		
		TextBox getMeasScoringInput();
		
		TextAreaWithMaxLength getRationaleInput();
		
		TextBox getVersionInput();
		
		ListBoxMVP getAuthorInput();
		
		TextBox getMeasureStewardOtherInput();
		
		TextAreaWithMaxLength getDescriptionInput();
		
		TextAreaWithMaxLength getCopyrightInput();
		
		TextAreaWithMaxLength getDisclaimerInput();
		
		TextAreaWithMaxLength getRiskAdjustmentInput();
		
		TextAreaWithMaxLength getRateAggregationInput();
		
		TextAreaWithMaxLength getInitialPopInput();
		
		TextAreaWithMaxLength getDenominatorInput();
		
		TextAreaWithMaxLength getDenominatorExclusionsInput();
		
		TextAreaWithMaxLength getNumeratorInput();
		
		TextAreaWithMaxLength getNumeratorExclusionsInput();
		
		TextAreaWithMaxLength getDenominatorExceptionsInput();
		
		TextAreaWithMaxLength getStratificationInput();
		
		TextAreaWithMaxLength getGuidanceInput();
		
		Button getAddRowButton();
		
		void addRow(FlexTable reference);
		
		FlexTable getReferenceTable();

		/**
		 * Gets the patient based input label
		 */
		TextBox getPatientBasedInput();

		ListBoxMVP getStewardListBox();

		ListBoxMVP getEndorsedByListBox();

		Button getTopDeleteMeasureButton();

		Button getTopSaveButton();

		MessageAlert getTopSuccessMessage();

		MessageAlert getTopErrorMessage();

		void setOptionsInStewardList(List<MeasureSteward> allStewardList, boolean editable);
		
		void setPatientBasedMeasure(boolean isPatientBasedMeasure);
		
		HelpBlock getHelpBlock();
		
	}
	
	/**
	 * The Interface AddEditAuthorsDisplay.
	 */
	public static interface AddEditAuthorsDisplay {
		
		/**
		 * Gets the author selected list.
		 *
		 * @return the author selected list
		 */
		List<Author> getAuthorSelectedList();
		
		/**
		 * Gets the list of all author.
		 *
		 * @return the list of all author
		 */
		List<Author> getListOfAllAuthor();
		
		/**
		 * Sets the author selected list.
		 *
		 * @param authorSelectedList the new author selected list
		 */
		void setAuthorSelectedList(List<Author> authorSelectedList);
		
		/**
		 * Builds the author cell table.
		 *
		 * @param authorSelectedList the author selected list
		 * @param editable the editable
		 * @param authorSelectedList2 the author selected list2
		 */
		public void buildAuthorCellTable(List<Author> authorSelectedList,
				boolean editable, List<Author> authorSelectedList2);
		
		/**
		 * As widget.
		 *
		 * @return the widget
		 */
		Widget asWidget();
	}
	
	/**
	 * The Interface AddEditComponentMeasuresDisplay.
	 */
	public static interface AddEditComponentMeasuresDisplay {
		
		/**
		 * Gets the selected filter.
		 *
		 * @return the selected filter
		 */
		int getSelectedFilter();
		
		/**
		 * Gets the ret button.
		 *
		 * @return the ret button
		 */
		public Button getRetButton();
		
		/**
		 * Gets the addto component measures button handler.
		 *
		 * @return the addto component measures button handler
		 */
		HasClickHandlers getApplytoComponentMeasuresButtonHandler();
		
		/**
		 * Gets the search button.
		 *
		 * @return the search button
		 */
		HasClickHandlers getSearchButton();
		
		/**
		 * Gets the search string.
		 *
		 * @return the search string
		 */
		HasValue<String> getSearchString();
		
		/* (non-Javadoc)
		 * @see mat.client.measure.metadata.BaseMetaDataPresenter.BaseAddEditDisplay#getSuccessMessageDisplay()
		 */
		/**
		 * Gets the success message display.
		 *
		 * @return the success message display
		 */
		public SuccessMessageAlert getSuccessMessageDisplay();
		
		/**
		 * Gets the addto component measures btn.
		 *
		 * @return the addto component measures btn
		 */
		public Button getApplytoComponentMeasuresBtn();
		
		/**
		 * Gets the component measures list.
		 *
		 * @return the component measures list
		 */
		List<Result> getComponentMeasuresList();
		
		/**
		 * Builds the cell table.
		 *
		 * @param result the result
		 * @param searchText the search text
		 * @param measureSelectedList the measure selected list
		 */
		void buildCellTable(ManageMeasureSearchModel result, final String searchText, List<ManageMeasureSearchModel.Result> measureSelectedList);
		
		/**
		 * Gets the return button.
		 *
		 * @return the return button
		 */
		HasClickHandlers getReturnButton();
		
		/**
		 * As widget.
		 *
		 * @return the widget
		 */
		Widget asWidget();
	}
	
	/** The panel. */
	private SimplePanel panel = new SimplePanel();
	
	/** The meta data display. */
	private MetaDataDetailDisplay metaDataDisplay;
	
	private HandlerRegistration nqfHandlerRegistration;
	
	/** The current measure detail. */
	private ManageMeasureDetailModel currentMeasureDetail;
	
	/** The current authors list. */
	private ManageAuthorsModel currentAuthorsList;
	
	/** The author list. */
	private List<Author> authorList = new ArrayList<Author>();
	
	/** The measure type list. */
	private List<MeasureType> measureTypeList = new ArrayList<MeasureType>();
	
	/** The db author list. */
	private List<Author> dbAuthorList = new ArrayList<Author>();
	
	/** The db measure type list. */
	private List<MeasureType> dbMeasureTypeList = new ArrayList<MeasureType>();
	
	/** The db qdm selected list. */
	private List<QualityDataSetDTO> dbQDMSelectedList = new ArrayList<QualityDataSetDTO>();
	
	/** The db component measures selected list. */
	private List<ManageMeasureSearchModel.Result> dbComponentMeasuresSelectedList = new ArrayList<ManageMeasureSearchModel.Result>();
	
	/**
	 * Gets the db component measures selected list.
	 *
	 * @return the db component measures selected list
	 */
	public List<ManageMeasureSearchModel.Result> getDbComponentMeasuresSelectedList() {
		return dbComponentMeasuresSelectedList;
	}
	
	/**
	 * Sets the db component measures selected list.
	 *
	 * @param dbComponentMeasuresSelectedList the new db component measures selected list
	 */
	public void setDbComponentMeasuresSelectedList(List<ManageMeasureSearchModel.Result> dbComponentMeasuresSelectedList) {
		this.dbComponentMeasuresSelectedList = dbComponentMeasuresSelectedList;
	}
	
	/** The empty widget. */
	private SimplePanel emptyWidget = new SimplePanel();
	
	/** The previous continue buttons. */
	private HasVisible previousContinueButtons;
	
	/** The last request time. */
	private long lastRequestTime;
	
	/** The max emeasure id. */
	private int maxEmeasureId;
	
	/** The editable. */
	private boolean editable = false;
	
	/** The is sub view. */
	private boolean isSubView = false;
	
	/** The measure xml model. */
	private MeasureXmlModel measureXmlModel;
	
	/** The is measure details loaded. */
	private boolean isMeasureDetailsLoaded = false;
	
	/** The service. */
	private MeasureServiceAsync service = MatContext.get().getMeasureService();
	
	/**
	 * Instantiates a new meta data presenter.
	 *
	 * @param mDisplay the m display
	 * @param cmDisplay the cm display
	 * @param pcButtons the pc buttons
	 * @param lp the lp
	 */
	public MetaDataPresenter(MetaDataDetailDisplay mDisplay, HasVisible pcButtons, ListBoxCodeProvider lp) {
		previousContinueButtons = pcButtons;
		metaDataDisplay = mDisplay;
		
		addHandlersToMetaDataDisplay();
		emptyWidget.add(new Label("No Measure Selected"));
	}
	
	/**
	 * Add All Event handlers to Metadata Display components.
	 */
	private void addHandlersToMetaDataDisplay() {
		HandlerManager eventBus = MatContext.get().getEventBus();
		
		
		metaDataDisplay.getBottomDeleteMeasureButton().addClickHandler(buildDeleteClickHandler(metaDataDisplay.getBottomErrorMessage()));
		metaDataDisplay.getTopDeleteMeasureButton().addClickHandler(buildDeleteClickHandler(metaDataDisplay.getTopErrorMessage()));
		
		metaDataDisplay.getMeasurementFromPeriodInputBox().addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				metaDataDisplay.getBottomErrorMessage().clearAlert();
				metaDataDisplay.getTopErrorMessage().clearAlert();
			}
		});
		
		metaDataDisplay.getMeasurementToPeriodInputBox().addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				metaDataDisplay.getBottomErrorMessage().clearAlert();
				metaDataDisplay.getTopErrorMessage().clearAlert();
			}
		});
		
		metaDataDisplay.getSearchButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				System.out.println("Search String: "+ metaDataDisplay.getSearchString().getValue());
			}
		});
		
		metaDataDisplay.getSaveButtonHasClickHandlers().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				saveMetaDataInformation(true, metaDataDisplay.getBottomSaveButton(), metaDataDisplay.getBottomSuccessMessage(), metaDataDisplay.getBottomErrorMessage());
			}
		});
		
		metaDataDisplay.getTopSaveButton().addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				saveMetaDataInformation(true, metaDataDisplay.getTopSaveButton(), metaDataDisplay.getTopSuccessMessage(), metaDataDisplay.getTopErrorMessage());
			}
		});
		
		metaDataDisplay.getFocusPanel().addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				//control-alt-s is save
				if (event.isAltKeyDown() && event.isControlKeyDown() && (event.getNativeKeyCode() == 83)) {
					saveMetaDataInformation(true, metaDataDisplay.getBottomSaveButton(), metaDataDisplay.getBottomSuccessMessage(), metaDataDisplay.getBottomErrorMessage());
				}
			}
		});
		
		//This event will be called when measure has been selected from measureLibrary
		eventBus.addHandler(MeasureSelectedEvent.TYPE, new MeasureSelectedEvent.Handler() {
			@Override
			public void onMeasureSelected(MeasureSelectedEvent event) {
				isMeasureDetailsLoaded = false;
				if (event.getMeasureId() != null) {
					isMeasureDetailsLoaded = true;
					getMeasureAndLogRecentMeasure();
				} else {
					displayEmpty();
				}
			}
		});
		
		metaDataDisplay.getGenerateEmeasureIdButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				generateAndSaveNewEmeasureid();
			}
		});
		
		metaDataDisplay.getAuthorListBox().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getMeasureTypeListBox().addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});

		metaDataDisplay.getNQFIDInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getDescriptionInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getCopyrightInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getDisclaimerInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getStratificationInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getRiskAdjustmentInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getRateAggregationInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getRationaleInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getClinicalStmtInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getImprovementNotationInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getReferenceInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getDefinitionsInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getGuidanceInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getTransmissionFormatInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getInitialPopInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getDenominatorInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getDenominatorExclusionsInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getNumeratorInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getNumeratorExclusionsInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getDenominatorExceptionsInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getMeasurePopulationInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getMeasurePopulationExclusionsInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getMeasureObservationsInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getSupplementalDataInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getSetNameInput().addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
		
		metaDataDisplay.getAddRowButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				metaDataDisplay.addRow(metaDataDisplay.getReferenceTable());
				metaDataDisplay.getSaveErrorMsg().clearAlert();
			}
		});
	}

	protected void saveMetaDataInformation(final boolean dispSuccessMsg, Button saveButton, MessageAlert successMessage, MessageAlert errorMessage) {
		clearMessages();
		
		if (MatContext.get().getMeasureLockService().checkForEditPermission() && checkIfCalenderYear(errorMessage)) {
			updateModelDetailsFromView();
			Mat.showLoadingMessage();
			MatContext.get().getSynchronizationDelegate().setSavingMeasureDetails(true);
			currentMeasureDetail.scrubForMarkUp();
			MatContext.get().getMeasureService().saveMeasureDetails(currentMeasureDetail,
					new AsyncCallback<SaveMeasureResult>() {
				
				@Override
				public void onSuccess(SaveMeasureResult result) {
					Mat.hideLoadingMessage();
					MatContext.get().getSynchronizationDelegate().setSavingMeasureDetails(false);
					if (result.isSuccess()) {
						MatContext.get().getMeasureService().getMeasure(MatContext.get().getCurrentMeasureId(), new AsyncCallback<ManageMeasureDetailModel>() {
							
							@Override
							public void onFailure(Throwable caught) {}//do nothing
							
							@Override
							public void onSuccess(ManageMeasureDetailModel result) {
								currentMeasureDetail = result;
								displayDetail();
								if (dispSuccessMsg) {
									successMessage.createAlert(MatContext.get().getMessageDelegate().getChangesSavedMessage());
								}
							}
						});
					} else {
						String alertMessage = MessageDelegate.getMeasureSaveServerErrorMessage(result.getFailureReason());
						if(result.getMessages().contains(MessageDelegate.NQF_NUMBER_REQUIRED_ERROR)) {
							alertMessage = MessageDelegate.NQF_NUMBER_REQUIRED_ERROR;
						}
	
						errorMessage.createAlert(alertMessage);
					}
					saveButton.setFocus(true);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Mat.hideLoadingMessage();
					MatContext.get().getSynchronizationDelegate().setSavingMeasureDetails(false);
					errorMessage.createAlert(caught.getLocalizedMessage());
				}
			});
		}
	}

	private ClickHandler buildDeleteClickHandler(MessageAlert errorMessageAlert) {
		ClickHandler deleteClickHandler = new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(isMeasureDeletable()){
					final DeleteConfirmDialogBox dialogBox = new DeleteConfirmDialogBox();
					dialogBox.showDeletionConfimationDialog(MatContext.get().getMessageDelegate().getDELETE_MEASURE_WARNING_MESSAGE());
					dialogBox.getConfirmbutton().addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(final ClickEvent event) {
							checkPasswordForMeasureDeletion(dialogBox.getPasswordEntered(), errorMessageAlert);
						}
					});
				}
			}
		};
		return deleteClickHandler;
	}
	
	/**
	 * Gets the component measures.
	 *
	 * @return the component measures
	 */
	public final void getComponentMeasures(){
			MatContext
			.get()
			.getMeasureService().getComponentMeasures(MatContext.get().getCurrentMeasureId(), new AsyncCallback<ManageMeasureSearchModel>() {
				
				@Override
				public void onFailure(Throwable caught) {
				}
				
				@Override
				public void onSuccess(ManageMeasureSearchModel result) {
					List<ManageMeasureSearchModel.Result> measureSelectedList = result.getData();
					metaDataDisplay.setComponentMeasureSelectedList(measureSelectedList);// Added To fix the Dirty Check Message issue
					metaDataDisplay.buildComponentMeasuresSelectedList(measureSelectedList, editable);
				}
			});
	}
			
	/**
	 * Gets the all measure types.
	 *
	 * @return the all measure types
	 */
	private void getAllMeasureTypes() {
		service.getAllMeasureTypes(new AsyncCallback<List<MeasureType>>() {
			
			@Override
			public void onSuccess(List<MeasureType> result) {
				metaDataDisplay.buildMeasureTypeCellTable(result, editable);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// do nothing
			}
		});
	}
	
	/**
	 * Fire successful deletion event.
	 * 
	 * @param isSuccess
	 *            the is success
	 * @param message
	 *            the message
	 */
	private void fireSuccessfullDeletionEvent(boolean isSuccess, String message){
		MeasureDeleteEvent deleteEvent = new MeasureDeleteEvent(isSuccess, message);
		MatContext.get().getEventBus().fireEvent(deleteEvent);
	}
	
	/**
	 * Fire back to measure library event.
	 */
	private void fireBackToMeasureLibraryEvent() {
		BackToMeasureLibraryPage backToMeasureLibraryPage = new BackToMeasureLibraryPage();
		MatContext.get().getEventBus().fireEvent(backToMeasureLibraryPage);
	}
	
	
	/**
	 * Generate and save new emeasureid.
	 */
	private void generateAndSaveNewEmeasureid() {
		if(editable && currentMeasureDetail.geteMeasureId()==0){
			MatContext.get().getMeasureService().generateAndSaveMaxEmeasureId(currentMeasureDetail, new AsyncCallback<Integer>() {
				
				@Override
				public void onFailure(Throwable caught) {
					metaDataDisplay.getBottomErrorMessage().createAlert(MatContext.get().getMessageDelegate().getGenericErrorMessage());
					MatContext.get().recordTransactionEvent(null, null, null, "Unhandled Exception: "+caught.getLocalizedMessage(), 0);
				}
				
				@Override
				public void onSuccess(Integer result) {
					if(result > 0){
						maxEmeasureId = result.intValue();
						if (maxEmeasureId < 1000000) {
							metaDataDisplay.setGenerateEmeasureIdButtonEnabled(false);
							metaDataDisplay.getEmeasureId().setValue(maxEmeasureId + "");
							((TextBox) metaDataDisplay.getEmeasureId()).setFocus(true);
							currentMeasureDetail.seteMeasureId(maxEmeasureId);
						}
					}
				}
			});
		}
	}
	
	/* (non-Javadoc)
	 * @see mat.client.MatPresenter#getWidget()
	 */
	@Override
	public Widget getWidget() {
		return panel;
	}
	
	/**
	 * Display empty.
	 */
	private void displayEmpty() {
		previousContinueButtons.setVisible(false);
		panel.clear();
		panel.add(emptyWidget);
	}
	
	/**
	 * Display detail.
	 */
	public void displayDetail() {
		previousContinueButtons.setVisible(true);
		panel.clear();
		metaDataDisplay.setMeasureScoringType(currentMeasureDetail.getMeasScoring());
		metaDataDisplay.setPatientBasedMeasure(currentMeasureDetail.isPatientBased());
		metaDataDisplay.buildForm();
		prepopulateFields();
		if(nqfHandlerRegistration == null) {
			nqfHandlerRegistration = metaDataDisplay.getEndorsedByListBox().addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					setNQFTitle((metaDataDisplay.getEndorsedByListBox().getSelectedIndex() != 0));
				}
			});
		}

		if (editable) {
			if ("0".equals(metaDataDisplay.getEmeasureId().getValue())) {
				metaDataDisplay.setGenerateEmeasureIdButtonEnabled(true);
				metaDataDisplay.getEmeasureId().setValue("");
			} else if (metaDataDisplay.getEmeasureId() != null) {
				metaDataDisplay.setGenerateEmeasureIdButtonEnabled(false);
			}
		} else {
			metaDataDisplay.setGenerateEmeasureIdButtonEnabled(false);
			if ("0".equals(metaDataDisplay.getEmeasureId().getValue())) {
				metaDataDisplay.getEmeasureId().setValue("");
			}
		}
		
		panel.add(metaDataDisplay.asWidget());
	}
	
	private void setNQFTitle(boolean endorsedByNQF) {
		if(!endorsedByNQF) {
			metaDataDisplay.getHelpBlock().setText("You have chosen no; the NQF number field has been cleared. It now reads as Not Applicable and is disabled.");
			metaDataDisplay.getNQFIDInput().setPlaceholder(MatConstants.NOT_APPLICABLE);
			metaDataDisplay.getNQFIDInput().setTitle(MatConstants.NOT_APPLICABLE);
			metaDataDisplay.getNQFIDInput().setText("");
			metaDataDisplay.getNQFIDInput().setReadOnly(true);
		} else {
			metaDataDisplay.getHelpBlock().setText("You have chosen yes, the NQF number field is now enabled and required.");
			if(!StringUtility.isEmptyOrNull(metaDataDisplay.getNQFIDInput().getText())) {
				String placeHolderText = metaDataDisplay.getNQFIDInput().getText();
				metaDataDisplay.getNQFIDInput().setPlaceholder(placeHolderText);
				metaDataDisplay.getNQFIDInput().setTitle(placeHolderText);
			} else {
				metaDataDisplay.getNQFIDInput().setPlaceholder(MatConstants.ENTER_NQF_NUMBER);
				metaDataDisplay.getNQFIDInput().setTitle(MatConstants.ENTER_NQF_NUMBER);
			}
			metaDataDisplay.getNQFIDInput().setReadOnly(false);
		}
	}
	
	/**
	 * Back to detail.
	 */  
	public void backToDetail() {
		previousContinueButtons.setVisible(true);
		panel.clear();
		panel.add(metaDataDisplay.asWidget());
		Mat.focusSkipLists("MeasureComposer");
	}
	
	/**
	 * Prepopulate fields.
	 */
	private void prepopulateFields() {
		editable = MatContext.get().getMeasureLockService().checkForEditPermission();
		metaDataDisplay.getNqfId().setValue(currentMeasureDetail.getNqfId());
		
		
		metaDataDisplay.geteMeasureIdentifier().setText(currentMeasureDetail.getMeasureSetId());
		metaDataDisplay.geteMeasureIdentifier().setTitle(currentMeasureDetail.getMeasureSetId());
		metaDataDisplay.getSetName().setValue(currentMeasureDetail.getGroupName());
		// short name is the Abbreviated Title
		metaDataDisplay.getShortName().setText(currentMeasureDetail.getShortName());
		metaDataDisplay.getShortName().setTitle(currentMeasureDetail.getShortName());
		metaDataDisplay.getMeasureScoring().setText(currentMeasureDetail.getMeasScoring());
		metaDataDisplay.getMeasureScoring().setTitle(currentMeasureDetail.getMeasScoring());
		
		// set the patient based input label
		if(currentMeasureDetail.isPatientBased()) {
			metaDataDisplay.getPatientBasedInput().setText("Yes");
			metaDataDisplay.getPatientBasedInput().setTitle("Yes");			
		} else {
			metaDataDisplay.getPatientBasedInput().setText("No");
			metaDataDisplay.getPatientBasedInput().setTitle("No");		
		}
		
		metaDataDisplay.getClinicalRecommendation().setValue(currentMeasureDetail.getClinicalRecomms());
		metaDataDisplay.getDefinitions().setValue(currentMeasureDetail.getDefinitions());
		metaDataDisplay.getDescription().setValue(currentMeasureDetail.getDescription());
		metaDataDisplay.getDisclaimer().setValue(currentMeasureDetail.getDisclaimer());
		metaDataDisplay.getRiskAdjustment().setValue(currentMeasureDetail.getRiskAdjustment());
		metaDataDisplay.getRateAggregation().setValue(currentMeasureDetail.getRateAggregation());
		metaDataDisplay.getInitialPop().setValue(currentMeasureDetail.getInitialPop());
		metaDataDisplay.getDenominator().setValue(currentMeasureDetail.getDenominator());
		metaDataDisplay.getDenominatorExclusions().setValue(currentMeasureDetail.getDenominatorExclusions());
		metaDataDisplay.getNumerator().setValue(currentMeasureDetail.getNumerator());
		metaDataDisplay.getNumeratorExclusions().setValue(currentMeasureDetail.getNumeratorExclusions());
		metaDataDisplay.getDenominatorExceptions().setValue(currentMeasureDetail.getDenominatorExceptions());
		metaDataDisplay.getMeasurePopulation().setValue(currentMeasureDetail.getMeasurePopulation());
		metaDataDisplay.getMeasureObservations().setValue(currentMeasureDetail.getMeasureObservations());
		metaDataDisplay.getMeasurePopulationExclusions().setValue(currentMeasureDetail.getMeasurePopulationExclusions());
		
		boolean isEndorsedByNQF = currentMeasureDetail.getEndorseByNQF();
		
		if(isEndorsedByNQF){
			metaDataDisplay.getEndorsedByListBox().setSelectedIndex(1);
		} else {
			metaDataDisplay.getEndorsedByListBox().setSelectedIndex(0);
		}
		
		setNQFTitle(isEndorsedByNQF);
		
		metaDataDisplay.getCopyright().setValue(currentMeasureDetail.getCopyright());
		
		metaDataDisplay.getGuidance().setValue(currentMeasureDetail.getGuidance());
		metaDataDisplay.getTransmissionFormat().setValue(currentMeasureDetail.getTransmissionFormat());
		metaDataDisplay.getImprovementNotation().setValue(currentMeasureDetail.getImprovNotations());
		metaDataDisplay.getSupplementalData().setValue(currentMeasureDetail.getSupplementalData());
		metaDataDisplay.getFinalizedDate().setText(currentMeasureDetail.getFinalizedDate());
		metaDataDisplay.getFinalizedDate().setTitle(currentMeasureDetail.getFinalizedDate());
		metaDataDisplay.getCalenderYear().setValue(currentMeasureDetail.isCalenderYear());
		if (metaDataDisplay.getCalenderYear().getValue().equals(Boolean.FALSE)) {
			metaDataDisplay.getMeasurementFromPeriodInputBox().setValue(currentMeasureDetail.getMeasFromPeriod());
			metaDataDisplay.getMeasurementToPeriodInputBox().setValue(currentMeasureDetail.getMeasToPeriod());
		} else {
			metaDataDisplay.getMeasurementFromPeriodInputBox().setValue(null);
			metaDataDisplay.getMeasurementToPeriodInputBox().setValue(null);
		}
		metaDataDisplay.getVersionNumber().setText(currentMeasureDetail.getVersionNumber());
		metaDataDisplay.getVersionNumber().setTitle(currentMeasureDetail.getVersionNumber());
		metaDataDisplay.getRationale().setValue(currentMeasureDetail.getRationale());
		metaDataDisplay.getStratification().setValue(currentMeasureDetail.getStratification());
		metaDataDisplay.getRiskAdjustment().setValue(currentMeasureDetail.getRiskAdjustment());
		
		// steward list
		if(currentMeasureDetail.getMeasureDetailResult().getUsedSteward() != null) {
			metaDataDisplay.setStewardId(currentMeasureDetail.getMeasureDetailResult().getUsedSteward().getId());
			metaDataDisplay.setStewardValue((currentMeasureDetail.getMeasureDetailResult().getUsedSteward().getOrgName()));
		} else {
			metaDataDisplay.setStewardId(null);
			metaDataDisplay.setStewardValue(null);
		}
		
		// author list
		if(currentMeasureDetail.getAuthorSelectedList() != null) {
			metaDataDisplay.setAuthorsSelectedList(currentMeasureDetail.getMeasureDetailResult().getUsedAuthorList());
		} else {
			List<Author> authorList = new ArrayList<Author>();
			metaDataDisplay.setAuthorsSelectedList(authorList);
			currentMeasureDetail.setAuthorSelectedList(authorList);
		}
		dbAuthorList.clear();
		dbAuthorList.addAll(currentMeasureDetail.getAuthorSelectedList());
		metaDataDisplay.setOptionsInStewardList(currentMeasureDetail.getMeasureDetailResult().getAllStewardList(), editable);
		metaDataDisplay.buildAuthorCellTable(currentMeasureDetail.getMeasureDetailResult().getAllAuthorList(), editable);
		
		//measureTypeSelectList
		if (currentMeasureDetail.getMeasureTypeSelectedList() != null) {
			metaDataDisplay.setMeasureTypeSelectedList(currentMeasureDetail.getMeasureTypeSelectedList());
		} else {
			List<MeasureType> measureTypeList = new ArrayList<MeasureType>();
			metaDataDisplay.setMeasureTypeSelectedList(measureTypeList);
			currentMeasureDetail.setMeasureTypeSelectedList(measureTypeList);
		}
		dbMeasureTypeList.clear();
		dbMeasureTypeList.addAll(currentMeasureDetail.getMeasureTypeSelectedList());
		getAllMeasureTypes();
		measureTypeList = currentMeasureDetail.getMeasureTypeSelectedList();
		
		if (currentMeasureDetail.getQdsSelectedList() != null) {
			metaDataDisplay.setQdmSelectedList(currentMeasureDetail.getQdsSelectedList());
		} else {
			List<QualityDataSetDTO> qdmList = new ArrayList<QualityDataSetDTO>();
			metaDataDisplay.setQdmSelectedList(qdmList);
			currentMeasureDetail.setQdsSelectedList(qdmList);
		}
		dbQDMSelectedList.clear();
		dbQDMSelectedList.addAll(currentMeasureDetail.getQdsSelectedList());
		
		//Component Measures List
		if (currentMeasureDetail.getComponentMeasuresSelectedList() != null) {
			metaDataDisplay.setComponentMeasureSelectedList(currentMeasureDetail.getComponentMeasuresSelectedList());
		} else {
			List<ManageMeasureSearchModel.Result> componentMeasuresList = new ArrayList<ManageMeasureSearchModel.Result>();
			metaDataDisplay.setComponentMeasureSelectedList(componentMeasuresList);
			currentMeasureDetail.setComponentMeasuresSelectedList(componentMeasuresList);
		}
		getComponentMeasures();
		dbComponentMeasuresSelectedList.clear();
		dbComponentMeasuresSelectedList.addAll(currentMeasureDetail.getComponentMeasuresSelectedList());
		
		if (currentMeasureDetail.getReferencesList() != null) {
			metaDataDisplay.setReferenceValues(currentMeasureDetail.getReferencesList(), editable);
		} else {
			metaDataDisplay.setReferenceValues(new ArrayList<String>(), editable);
		}
		metaDataDisplay.setAddEditButtonsVisible(editable);
		ReadOnlyHelper.setReadOnlyForCurrentMeasure(metaDataDisplay.asWidget(), editable);
		metaDataDisplay.getEndorsedByListBox().setEnabled(editable);
		metaDataDisplay.setSaveButtonEnabled(editable);
		metaDataDisplay.getEmeasureId().setValue(currentMeasureDetail.geteMeasureId()+"");
		metaDataDisplay.getBottomDeleteMeasureButton().setEnabled(isMeasureDeletable());
		metaDataDisplay.getTopDeleteMeasureButton().setEnabled(isMeasureDeletable());
		metaDataDisplay.getStewardListBox().setEnabled(editable);
		currentMeasureDetail.setEditable(editable);
		if(metaDataDisplay.getCalenderYear().getValue().equals(Boolean.FALSE) && editable){
			metaDataDisplay.setMeasurementPeriodButtonsVisible(true);
		} else {
			metaDataDisplay.setMeasurementPeriodButtonsVisible(false);
		}
	}
	
	/**
	 * steward and Author table.
	 */
	public void setStewardAndMeasureDevelopers() {
		service.getUsedStewardAndDevelopersList(MatContext.get().getCurrentMeasureId(),
				new AsyncCallback<MeasureDetailResult>(){
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(MatContext.get().getMessageDelegate().getGenericErrorMessage());
			}
			
			@Override
			public void onSuccess(MeasureDetailResult result) {
				if(result.getUsedSteward() !=null){
					metaDataDisplay.setStewardId(result.getUsedSteward().getId());
					metaDataDisplay.setStewardValue(result.getUsedSteward().getOrgName());
					
				} else {
					metaDataDisplay.setStewardId(null);
					metaDataDisplay.setStewardValue(null);
				}
				
				if(currentMeasureDetail.getAuthorSelectedList() !=null){
					metaDataDisplay.setAuthorsSelectedList(result.getUsedAuthorList());
				} else {
					List<Author> authorList = new ArrayList<Author>();
					metaDataDisplay.setAuthorsSelectedList(authorList);
					currentMeasureDetail.setAuthorSelectedList(authorList);
				}
				dbAuthorList.clear();
				
				dbAuthorList.addAll(currentMeasureDetail.getAuthorSelectedList());
				metaDataDisplay.setOptionsInStewardList(result.getAllStewardList(),MatContext.get().getMeasureLockService().checkForEditPermission());
				
				metaDataDisplay.buildAuthorCellTable(result.getAllAuthorList(), editable);
			}
		});
	}
	
	/**
	 * Update model details from view.
	 */
	private void updateModelDetailsFromView() {
		updateModelDetailsFromView(currentMeasureDetail, metaDataDisplay);
	}
	
	/**
	 * Check if calender year.
	 *
	 * @return true, if successful
	 */
	private boolean checkIfCalenderYear(MessageAlert errorMessage){
		boolean isCalender = false;
		boolean isFromDateValid = true;
		boolean isToDateValid = true;
		if(metaDataDisplay.getCalenderYear().getValue().equals(Boolean.FALSE)){
			if(!metaDataDisplay.getMeasurementFromPeriod().isEmpty() &&
					!metaDataDisplay.getMeasurementToPeriod().isEmpty()){
				// MAT5069 - user can enter date in text box now, so validate "from" box
				if (!metaDataDisplay.getMeasurementFromPeriodInputBox().isDateValid()) {
					isFromDateValid = false;
					isCalender = false;
				}
				if (!metaDataDisplay.getMeasurementToPeriodInputBox().isDateValid()) {
					isToDateValid = false;
					isCalender = false;
				}
				// MAT5069 - Make sure that the Start Period >= From Period
				if (isFromDateValid && isToDateValid) {
					if (periodDatesValid(metaDataDisplay.getMeasurementFromPeriodInputBox(), metaDataDisplay.getMeasurementToPeriodInputBox())) {
						isCalender = true;
					} else {
						isCalender = false;
					}
				}
			} else {
				isCalender = false;
			}
		} else {
			isCalender = true;
		}
		// if error, put up error message
		if (!isCalender) {
			Mat.hideLoadingMessage();
			MatContext.get().getSynchronizationDelegate().setSavingMeasureDetails(false);
			errorMessage.createAlert(MatContext.get().getMessageDelegate().getMEASURE_PERIOD_DATES_ERROR());
		}
		return isCalender;
	}
	
	/**
	 * checks that the to date >= from date in Measurement Period
	 */
	private boolean periodDatesValid(DateBoxWithCalendar fromDateBox, DateBoxWithCalendar toDateBox){
		boolean valid = true;
		
		Date fromDate = fromDateBox.getDate();
		Date toDate = toDateBox.getDate();
		
		if ((fromDate == null) || (toDate == null)) {
			return false;
		}
		
		// check to see if the dates are the same day or not, assume both are in the same time zone
		if (fromDate.compareTo(toDate) > 0) {
			return false;
		}
		
		return valid;
	}
	
	
	/**
	 * Update model details from view.
	 * 
	 * @param currentMeasureDetail
	 *            the current measure detail
	 * @param metaDataDisplay
	 *            the meta data display
	 */
	public void updateModelDetailsFromView(ManageMeasureDetailModel currentMeasureDetail, MetaDataDetailDisplay metaDataDisplay) {
		currentMeasureDetail.setShortName(metaDataDisplay.getShortName().getText());
								
		currentMeasureDetail.setFinalizedDate(metaDataDisplay.getFinalizedDate().getText());
		currentMeasureDetail.setClinicalRecomms(metaDataDisplay.getClinicalRecommendation().getValue());
		currentMeasureDetail.setDefinitions(metaDataDisplay.getDefinitions().getValue());
		currentMeasureDetail.setDescription(metaDataDisplay.getDescription().getValue());
		currentMeasureDetail.setDisclaimer(metaDataDisplay.getDisclaimer().getValue());
		currentMeasureDetail.setRiskAdjustment(metaDataDisplay.getRiskAdjustment().getValue());
		currentMeasureDetail.setRateAggregation(metaDataDisplay.getRateAggregation().getValue());
		
		currentMeasureDetail.setMeasScoring(metaDataDisplay.getMeasureScoring().getText());
		
		currentMeasureDetail.setInitialPop(metaDataDisplay.getInitialPop().getValue() != ""
				? metaDataDisplay.getInitialPop().getValue() : null);
		currentMeasureDetail.setStratification(metaDataDisplay.getStratification().getValue() != ""
				? metaDataDisplay.getStratification().getValue() : null );
		if ((currentMeasureDetail.getMeasScoring() != null) && (currentMeasureDetail.getMeasScoring().equalsIgnoreCase("Ratio")
				|| currentMeasureDetail.getMeasScoring().equalsIgnoreCase("Proportion"))) {
			currentMeasureDetail.setDenominator(metaDataDisplay.getDenominator().getValue() != ""
					? metaDataDisplay.getDenominator().getValue() : null);
			currentMeasureDetail.setDenominatorExclusions(metaDataDisplay.getDenominatorExclusions().getValue() != ""
					? metaDataDisplay.getDenominatorExclusions().getValue() : null);
			currentMeasureDetail.setNumerator(metaDataDisplay.getNumerator().getValue() != ""
					? metaDataDisplay.getNumerator().getValue() : null);
			currentMeasureDetail.setNumeratorExclusions(metaDataDisplay.getNumeratorExclusions().getValue() != ""
					? metaDataDisplay.getNumeratorExclusions().getValue() : null);
		} else {
			currentMeasureDetail.setDenominator(null);
			currentMeasureDetail.setDenominatorExclusions(null);
			currentMeasureDetail.setNumerator(null);
			currentMeasureDetail.setNumeratorExclusions(null);
		}
		if ((currentMeasureDetail.getMeasScoring() != null)
				&& (currentMeasureDetail.getMeasScoring().equalsIgnoreCase("Proportion"))) {
			currentMeasureDetail.setDenominatorExceptions(
					metaDataDisplay.getDenominatorExceptions().getValue() != ""
					? metaDataDisplay.getDenominatorExceptions().getValue() : null);
		} else {
			currentMeasureDetail.setDenominatorExceptions(null);
		}
		if ((currentMeasureDetail.getMeasScoring() != null)
				&& (currentMeasureDetail.getMeasScoring().equalsIgnoreCase("Ratio") && !currentMeasureDetail.isPatientBased())
						|| currentMeasureDetail.getMeasScoring().equalsIgnoreCase("Continuous Variable")) {
			currentMeasureDetail.setMeasureObservations(metaDataDisplay.getMeasureObservations().getValue() != ""
					? metaDataDisplay.getMeasureObservations().getValue() : null);
		} else {
			currentMeasureDetail.setMeasureObservations(null);
		}
		if ((currentMeasureDetail.getMeasScoring() != null)
				&& (currentMeasureDetail.getMeasScoring().equalsIgnoreCase("Continuous Variable"))) {
			currentMeasureDetail.setMeasurePopulation(metaDataDisplay.getMeasurePopulation().getValue() != ""
					? metaDataDisplay.getMeasurePopulation().getValue() : null);
			currentMeasureDetail.setMeasurePopulationExclusions(metaDataDisplay.getMeasurePopulationExclusions().getValue() != ""
					? metaDataDisplay.getMeasurePopulationExclusions().getValue() : null);
		} else {
			currentMeasureDetail.setMeasurePopulation(null);
			currentMeasureDetail.setMeasurePopulationExclusions(null);
		}
		
		currentMeasureDetail.setCopyright(metaDataDisplay.getCopyright().getValue());
		if(metaDataDisplay.getEndorsedByListBox().getItemText(metaDataDisplay.getEndorsedByListBox().getSelectedIndex()).equalsIgnoreCase("Yes")){
			currentMeasureDetail.setEndorseByNQF(true);
			currentMeasureDetail.setNqfId(metaDataDisplay.getNqfId().getValue());
		} else {
			currentMeasureDetail.setEndorseByNQF(false);
			currentMeasureDetail.setNqfId("");
		}

		currentMeasureDetail.setGuidance(metaDataDisplay.getGuidance().getValue());
		currentMeasureDetail.setTransmissionFormat(metaDataDisplay.getTransmissionFormat().getValue());
		currentMeasureDetail.setImprovNotations(metaDataDisplay.getImprovementNotation().getValue());
		currentMeasureDetail.setCalenderYear(metaDataDisplay.getCalenderYear().getValue());
		currentMeasureDetail.setMeasFromPeriod(metaDataDisplay.getMeasurementFromPeriod());
		
		if(metaDataDisplay.getStewardListBox().getSelectedIndex() !=0){
			String sId= metaDataDisplay.getStewardListBox().getValue(metaDataDisplay.getStewardListBox().getSelectedIndex());
			metaDataDisplay.setStewardId(sId);
			String sValue= metaDataDisplay.getStewardListBox().getItemText(metaDataDisplay.getStewardListBox().getSelectedIndex());
			metaDataDisplay.setStewardValue(sValue);
		} else {
			metaDataDisplay.setStewardId(null);
			metaDataDisplay.setStewardValue(null);
		}
		currentMeasureDetail.setStewardId(metaDataDisplay.getStewardId());
		currentMeasureDetail.setStewardValue(metaDataDisplay.getStewardValue());
		
		currentMeasureDetail.setMeasToPeriod(metaDataDisplay.getMeasurementToPeriod());
		currentMeasureDetail.setSupplementalData(metaDataDisplay.getSupplementalData().getValue());
		currentMeasureDetail.setRationale(metaDataDisplay.getRationale().getValue());
		currentMeasureDetail.setReferencesList(metaDataDisplay.getReferenceValues());
		currentMeasureDetail.setMeasureSetId(metaDataDisplay.geteMeasureIdentifier().getText());
		currentMeasureDetail.setGroupName(metaDataDisplay.getSetName().getValue());
		
		currentMeasureDetail.setRiskAdjustment(metaDataDisplay.getRiskAdjustment().getValue());
		currentMeasureDetail.setVersionNumber(metaDataDisplay.getVersionNumber().getText());
		currentMeasureDetail.setAuthorSelectedList(metaDataDisplay.getAuthorsSelectedList());
		currentMeasureDetail.setMeasureTypeSelectedList(measureTypeList);
		currentMeasureDetail.setQdsSelectedList(metaDataDisplay.getQdmSelectedList());
		currentMeasureDetail.setComponentMeasuresSelectedList(metaDataDisplay.getComponentMeasureSelectedList());
		currentMeasureDetail.setToCompareAuthor(dbAuthorList);
		currentMeasureDetail.setToCompareMeasure(dbMeasureTypeList);
		currentMeasureDetail.setToCompareComponentMeasures(dbComponentMeasuresSelectedList);
		
		if ((metaDataDisplay.getEmeasureId().getValue() != null) && !metaDataDisplay.getEmeasureId().getValue().equals("")) {
			currentMeasureDetail.seteMeasureId(new Integer(metaDataDisplay.getEmeasureId().getValue()));
		}
	}
	
	/* (non-Javadoc)
	 * @see mat.client.MatPresenter#beforeDisplay()
	 */
	@Override
	public void beforeDisplay() {
		activateButtons(false);
		if ((MatContext.get().getCurrentMeasureId() == null)
				|| MatContext.get().getCurrentMeasureId().equals("")) {
			displayEmpty();
		} else {
			panel.clear();
			panel.add(metaDataDisplay.asWidget());
			if (!isMeasureDetailsLoaded) { // this check is made so that when measure is clicked from Measure library, its not called twice.
				currentMeasureDetail = null;
				lastRequestTime = System.currentTimeMillis();
				getMeasureDetail();
			} else {
				isMeasureDetailsLoaded = false;
			}
		}
		getMeasureDetail();
		getAllMeasureTypes();
		MeasureComposerPresenter.setSubSkipEmbeddedLink("MetaDataView.containerPanel");
		Mat.focusSkipLists("MeasureComposer");
		clearMessages();
	}
	
	private void activateButtons(boolean shouldActivate) {
		if(shouldActivate) {
			Mat.hideLoadingMessage();
		} else {
			Mat.showLoadingMessage();
		}
		metaDataDisplay.getBottomDeleteMeasureButton().setEnabled(shouldActivate);
		metaDataDisplay.getBottomSaveButton().setEnabled(shouldActivate);
		metaDataDisplay.getTopDeleteMeasureButton().setEnabled(shouldActivate);
		metaDataDisplay.getTopSaveButton().setEnabled(shouldActivate);
		metaDataDisplay.getAddRowButton().setEnabled(shouldActivate);
		metaDataDisplay.getGenerateeMeasureIDButton().setEnabled(shouldActivate);
		metaDataDisplay.getSearchButton().setEnabled(shouldActivate);
	}

	/* (non-Javadoc)
	 * @see mat.client.MatPresenter#beforeClosingDisplay()
	 */
	@Override
	public void beforeClosingDisplay() {
		metaDataDisplay.getHelpBlock().setText("");
		panel.clear();
		editable=false;
		clearMessages();
		
	}
	
	/** Gets the measure and logs in this measure as recently used measure in recent measure activity log.
	 * 
	 * @return the measure and log recent measure */
	private void getMeasureAndLogRecentMeasure() {
		MatContext.get().getMeasureService().getMeasureAndLogRecentMeasure(MatContext.get().getCurrentMeasureId(),
				MatContext.get().getLoggedinUserId(), getAsyncCallBackForMeasureAndLogRecentMeasure());
	}
	
	/** Gets the measure detail.
	 * 
	 * @return the measure detail */
	private void getMeasureDetail(){
		MatContext.get().getMeasureService().getMeasure(MatContext.get().getCurrentMeasureId(), getAsyncCallBackForMeasureDetails());
	}
	
	/** Gets the async call back.
	 * 
	 * @return the async call back */
	private AsyncCallback<ManageMeasureDetailModel> getAsyncCallBackForMeasureAndLogRecentMeasure() {
		return new AsyncCallback<ManageMeasureDetailModel>() {
			final long callbackRequestTime = lastRequestTime;
			@Override
			public void onFailure(Throwable caught) {
				handleAsyncFailure(caught);
			}
			@Override
			public void onSuccess(ManageMeasureDetailModel result) {
				handleAsyncSuccess(result, callbackRequestTime);
			}
		};
	}
	
	private AsyncCallback<ManageMeasureDetailModel> getAsyncCallBackForMeasureDetails() {
		return new AsyncCallback<ManageMeasureDetailModel>() {
			final long callbackRequestTime = lastRequestTime;
			@Override
			public void onFailure(Throwable caught) {
				handleAsyncFailure(caught);
				activateButtons(true);
			}
			
			@Override
			public void onSuccess(ManageMeasureDetailModel result) {
				handleAsyncSuccess(result, callbackRequestTime);
				activateButtons(true);
			}

		};
	}
	private void handleAsyncFailure(Throwable caught) {
		metaDataDisplay.getBottomErrorMessage().createAlert(MatContext.get()
				.getMessageDelegate().getGenericErrorMessage());
		MatContext.get().recordTransactionEvent(null, null, null,
				"Unhandled Exception: " +caught.getLocalizedMessage(), 0);
	}
	
	private void handleAsyncSuccess(ManageMeasureDetailModel result, long callbackRequestTime) {
		if (callbackRequestTime == lastRequestTime) {
			currentMeasureDetail = result;
			displayDetail();
			fireMeasureEditEvent();
		}
	}
	
	/**
	 * Check password for measure deletion.
	 * 
	 * @param password
	 *            the password
	 * @param errorMessageAlert 
	 */
	private void checkPasswordForMeasureDeletion(String password, MessageAlert errorMessageAlert) {
		MatContext.get().getLoginService().isValidPassword(MatContext.get().getLoggedinLoginId(), password, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				fireBackToMeasureLibraryEvent();
				fireSuccessfullDeletionEvent(false, null);
			}
			
			@Override
			public void onSuccess(Boolean result) {
				if (result) {
					checkIfMeasureIsUsedAsAComponentMeasure(errorMessageAlert);
				} else {
					fireBackToMeasureLibraryEvent();
					fireSuccessfullDeletionEvent(false, MatContext.get().getMessageDelegate().getMeasureDeletionInvalidPwd());
				}
			}
		});
	}
	
	private void checkIfMeasureIsUsedAsAComponentMeasure(MessageAlert errorMessageAlert) {
		MatContext.get().getMeasureService().checkIfMeasureIsUsedAsComponentMeasure(MatContext.get().getCurrentMeasureId(), new AsyncCallback<GenericResult>(){
			@Override
			public void onFailure(Throwable caught) {
				fireBackToMeasureLibraryEvent();
				fireSuccessfullDeletionEvent(false, null);
			}
			
			@Override
			public void onSuccess(GenericResult result) {
				if (result.isSuccess()) {
					deleteMeasure();
				} else {
					String errorMessage = result.getMessages().get(0);
					errorMessageAlert.createAlert(errorMessage);
				}
			}
		});
	}
	
	/**
	 * Delete measure.
	 */
	private void deleteMeasure() {
		
		if ((currentMeasureDetail.getMeasureOwnerId() != null) && currentMeasureDetail.getMeasureOwnerId()
				.equalsIgnoreCase(MatContext.get().getLoggedinUserId())) {
			
			MatContext.get().getMeasureService().saveAndDeleteMeasure(MatContext.get().getCurrentMeasureId(), MatContext.get().getLoggedinLoginId(), new AsyncCallback<Void>(){
				@Override
				public void onFailure(Throwable caught) {
					fireBackToMeasureLibraryEvent();
					
					fireSuccessfullDeletionEvent(false, null);
				}
				
				@Override
				public void onSuccess(Void result) {
					MatContext.get().recordTransactionEvent(MatContext.get().getCurrentMeasureId(), null,
							"MEASURE_DELETE_EVENT", "Measure Successfully Deleted", ConstantMessages.DB_LOG);
					// this is set to avoid showing dirty check message if user has modified Measure details and is deleting without saving.
					currentMeasureDetail.setDeleted(true);
					MatContext.get().setMeasureDeleted(true);
					fireBackToMeasureLibraryEvent();
					fireSuccessfullDeletionEvent(true, MatContext.get().getMessageDelegate().getMeasureDeletionSuccessMgs());
					
				}
			});
		}
	}
	/**
	 * Fire measure edit event.
	 */
	private void fireMeasureEditEvent() {
		MeasureEditEvent evt = new MeasureEditEvent();
		MatContext.get().getEventBus().fireEvent(evt);
	}
	
	/**
	 * Clear messages.
	 */
	private void  clearMessages() {
		metaDataDisplay.getSaveErrorMsg().clearAlert();
		metaDataDisplay.getBottomErrorMessage().clearAlert();
		metaDataDisplay.getBottomSuccessMessage().clearAlert();
		metaDataDisplay.getTopErrorMessage().clearAlert();
		metaDataDisplay.getTopSuccessMessage().clearAlert();
	}
	
	/**
	 * Gets the meta data display.
	 * 
	 * @return the metaDataDisplay
	 */
	public MetaDataDetailDisplay getMetaDataDisplay() {
		return metaDataDisplay;
	}
	
	/**
	 * Sets the meta data display.
	 * 
	 * @param metaDataDisplay
	 *            the metaDataDisplay to set
	 */
	public void setMetaDataDisplay(MetaDataDetailDisplay metaDataDisplay) {
		this.metaDataDisplay = metaDataDisplay;
	}
	
	/**
	 * Gets the current measure detail.
	 * 
	 * @return the currentMeasureDetail
	 */
	public ManageMeasureDetailModel getCurrentMeasureDetail() {
		return currentMeasureDetail;
	}
	
	/**
	 * Gets the current authors list.
	 * 
	 * @return the currentAuthorsList
	 */
	public ManageAuthorsModel getCurrentAuthorsList() {
		return currentAuthorsList;
	}
	
	/**
	 * Sets the current authors list.
	 * 
	 * @param currentAuthorsList
	 *            the currentAuthorsList to set
	 */
	public void setCurrentAuthorsList(ManageAuthorsModel currentAuthorsList) {
		this.currentAuthorsList = currentAuthorsList;
	}
	
	/**
	 * Gets the author list.
	 * 
	 * @return the authorList
	 */
	public List<Author> getAuthorList() {
		return authorList;
	}
	
	/**
	 * Sets the author list.
	 * 
	 * @param authorList
	 *            the authorList to set
	 */
	public void setAuthorList(List<Author> authorList) {
		this.authorList = authorList;
	}
	
	/**
	 * Gets the db author list.
	 * 
	 * @return the dbAuthorList
	 */
	public List<Author> getDbAuthorList() {
		return dbAuthorList;
	}
	
	/**
	 * Sets the db author list.
	 * 
	 * @param dbAuthorList
	 *            the dbAuthorList to set
	 */
	public void setDbAuthorList(List<Author> dbAuthorList) {
		this.dbAuthorList = dbAuthorList;
	}
	
	/**
	 * Gets the db measure type list.
	 * 
	 * @return the dbMeasureTypeList
	 */
	public List<MeasureType> getDbMeasureTypeList() {
		return dbMeasureTypeList;
	}
	
	/**
	 * Sets the db measure type list.
	 * 
	 * @param dbMeasureTypeList
	 *            the dbMeasureTypeList to set
	 */
	public void setDbMeasureTypeList(List<MeasureType> dbMeasureTypeList) {
		this.dbMeasureTypeList = dbMeasureTypeList;
	}
	
	/**
	 * Gets the db qdm selected list.
	 *
	 * @return the db qdm selected list
	 */
	public List<QualityDataSetDTO> getDbQDMSelectedList() {
		return dbQDMSelectedList;
	}
	
	/**
	 * Sets the db qdm selected list.
	 *
	 * @param dbQDMSelectedList the new db qdm selected list
	 */
	public void setDbQDMSelectedList(List<QualityDataSetDTO> dbQDMSelectedList) {
		this.dbQDMSelectedList = dbQDMSelectedList;
	}
	
	/**
	 * Checks if is sub view.
	 * 
	 * @return the isSubView
	 */
	public boolean isSubView() {
		return isSubView;
	}
	
	/**
	 * Sets the sub view.
	 * 
	 * @param isSubView
	 *            the isSubView to set
	 */
	public void setSubView(boolean isSubView) {
		this.isSubView = isSubView;
	}
	
	/**
	 * Gets the measure xml model.
	 * 
	 * @return the measureXmlModel
	 */
	public MeasureXmlModel getMeasureXmlModel() {
		return measureXmlModel;
	}
	
	/**
	 * Sets the measure xml model.
	 * 
	 * @param measureXmlModel
	 *            the measureXmlModel to set
	 */
	public void setMeasureXmlModel(MeasureXmlModel measureXmlModel) {
		this.measureXmlModel = measureXmlModel;
	}

	/**
	 * Checks if is measure deletable.
	 *
	 * @return true, if is measure deletable
	 */
	private boolean isMeasureDeletable(){
		if ((currentMeasureDetail.getMeasureOwnerId() != null) && !currentMeasureDetail.getMeasureOwnerId()
				.equalsIgnoreCase(MatContext.get().getLoggedinUserId())) {
			return false;
		} 			
		return true;
	}

	/**
	 * Checked to see if the Measure Details page Data and the DB data are the
	 * same.
	 * 
	 * @param metaDataPresenter
	 *            the meta data presenter
	 * @return true, if is measure details same
	 */
	public boolean isMeasureDetailsSame() {
		ManageMeasureDetailModel pageData = new ManageMeasureDetailModel();
		updateModelDetailsFromView(pageData, getMetaDataDisplay());
		ManageMeasureDetailModel dbData = getCurrentMeasureDetail();
		if (dbData.isDeleted() || !dbData.isEditable()) {
			//dont show dirty check message when measure is deleted.
			return true;
		} else {
			pageData.setToCompareAuthor(pageData.getAuthorSelectedList());
			pageData.setToCompareMeasure(pageData.getMeasureTypeSelectedList());
			pageData.setToCompareComponentMeasures(pageData.getComponentMeasuresSelectedList());
			dbData.setToCompareAuthor(getDbAuthorList());
			dbData.setToCompareMeasure(getDbMeasureTypeList());
			dbData.setToCompareComponentMeasures(getDbComponentMeasuresSelectedList());
			return pageData.equals(dbData) && isNQFIDExists(pageData.getEndorseByNQF(), pageData.getNqfId());
		}
	}

	private boolean isNQFIDExists(boolean endorseByNQF, String nqfId) {
		return ((endorseByNQF && !StringUtility.isEmptyOrNull(nqfId)) || !endorseByNQF);
	}

	
	public boolean isMeasureDetailsValid() {
		if ((MatContext.get().getCurrentMeasureId() != null) && !MatContext.get().getCurrentMeasureId().equals("")
				&&!isMeasureDetailsSame()) {
			if (isSubView()) {
				backToDetail();
				getMetaDataDisplay().setSaveButtonEnabled(MatContext.get().getMeasureLockService().checkForEditPermission());
				getComponentMeasures();
				setStewardAndMeasureDevelopers();
			}
			return false;
		} else {
			return true;
		}
	}
}