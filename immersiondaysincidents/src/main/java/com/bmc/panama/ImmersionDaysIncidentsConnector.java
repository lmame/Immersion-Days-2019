package com.bmc.panama;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import com.bmc.arsys.api.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.bmc.panama.Incident;
import main.java.com.bmc.panama.IncidentResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bmc.panama.messages.request.*;
import com.bmc.panama.messages.result.*;
import com.bmc.panama.messages.result.LookupResult.PickEntry;
import com.bmc.panama.object.*;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.databind.util.ISO8601Utils;

public class ImmersionDaysIncidentsConnector extends Connector
{
    private static final Logger log = LogManager.getLogger("ImmersionDaysIncidentsConnector");
    private Map<String, Object> configParameters;
    // LMA:: The object mapper will be used to send the data as a stringified json.
    private ObjectMapper mapper;
    private static final String OUTPUT_GET_INCIDENTS = "all incidents";

    public ImmersionDaysIncidentsConnector( Driver driver, Profile profile )
    {
        super(driver, profile);
        // TODO: Auto-generated constructor stub
		
		// LMA:: The object mapper will be used to send the data as a stringified json.
        mapper = new ObjectMapper();
    }

    /**
     * Connector instance initialization
     *
     * This function should be overridden by subclasses if any initialization is required
     * for each connector instance.
     * This function can be used to check the validity of configuration, get the server info from configuration,
     * create server object and test if connection to server is working.
     *
     * @param configuration
     * @throws Exception
     * @returns {Object} promise with success or error.
     * @api public
     */
    @Override
    public void initialize( Configuration configuration ) throws Exception
    {
        log.debug("Initializing  Connector");

        // TODO: Check here service availability
        // Example:
        if ((configuration == null) || (configuration.getParameters() == null))
        {
            log.error("Missing connector configuration");
            throw new IllegalArgumentException("Missing connector configuration");
        }
        Map<String, Object> config_params = configuration.getParameters();
        String serverHost = (String) config_params.get("host");
        String serverPortStr = (String) config_params.get("port");
        if ((serverPortStr == null) || serverPortStr.isEmpty()) serverPortStr = "0";
        Integer serverPort = 0;
        try
        {
            serverPort = Integer.valueOf(serverPortStr);
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Invalid Server port \""+serverPortStr+"\" specified");
        }
        // Create Server Instance
        // Test connectivity with server and throw error if not available
    }

    /**
     * Trigger Poll request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     * Used for trigger type POLLED which periodically polls the server for data.
     *
     * @param request The trigger poll request
     * @return
     * @throws Exception
     * @api public
     */
    @Override
    public TriggerResult triggerPoll( TriggerPollRequest request )
    {
        log.debug("Processing Trigger Poll Request: "+request.getTrigger());
        TriggerResult result;
        ArrayList<FlowError> errors = new ArrayList<FlowError>();
        PanamaRegistration.Trigger trigger = request.getFlow().getTrigger().getOperation();

        if (trigger != null)
        {
            Conditions conditions = new Conditions(request.getConditions(), trigger.getConditions());
            switch (request.getTrigger()) {
                //case "UpdatedIssues":
                //    result = getUpdatedIssues(request, trigger, conditions);
                //    break;



                default:
                    errors.add(new FlowError("UnknownTrigger", "Unknown trigger poll requested: "+request.getTrigger(), null, null));
                    result = new TriggerResult(request, 0, 0, errors);
            }
        }
        else
        {
            errors.add(new FlowError("UnknownTrigger", "Unknown trigger poll requested: "+request.getTrigger(), null, null));
            result = new TriggerResult(request, 0, 0, errors );
        }
        return result;
    }

    /**
     * Trigger Listen request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     * Used when trigger type is LISTENING. Which is used for Listening on a persistent
     * (that is, actively connected) connection that streams data.
     * Can be used to process streamed data.
     *
     * @param request The trigger listen request
     * @returns {Object} promise with trigger_result or error.
     * @api public
     */
    @Override
    public TriggerResult triggerListen( TriggerListenRequest request )
    {
        log.error("Connector:triggerListen is not implemented in this connector");
        ArrayList<FlowError> errors = new ArrayList<FlowError>();
        errors.add(new FlowError("NotImplemented", "Connector:triggerListen is not implemented in this connector", null, null));
        TriggerResult result = new TriggerResult(request, 0, 0, errors );
        return result;
    }

    /**
     * Trigger Subscribe request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     * Used for trigger types HOOK and EMAIL.
     * Can be used to subscribe to webhook or email notifications.
     *
     * @param {String} flowId The flow Identifier.
     * @param {String} runId The flow run Identifier.
     * @param {String} trigger The trigger name.
     * @param {[Object]} parameters The trigger parameters (if any).
     * @param {[Object]} conditions The trigger conditions.
     * @returns {Object} promise with trigger_result or error.
     * @api public
     */
    public TriggerResult triggerSubscribe( TriggerSubscribeRequest request) throws Error
    {
        log.error("Connector:triggerSubscribe is not implemented in this connector");
        ArrayList<FlowError> errors = new ArrayList<FlowError>();
        errors.add(new FlowError("NotImplemented", "Connector:triggerSubscribe is not implemented in this connector", null, null));
        TriggerResult result = new TriggerResult(request, errors );
        return result;
    }

    /**
     * Trigger Data request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     * Used for trigger types HOOK and EMAIL.
     * Can be used to get the data received from subscribed webhook or email notification.
     *
     * @param {String} flowId The flow Identifier.
     * @param {String} runId The flow run Identifier.
     * @param {String} trigger The trigger name.
     * @param {[Object]} conditions The trigger conditions.
     * @returns {Object} promise with trigger_result or error.
     * @api public
     */
    public TriggerResult triggerData( TriggerDataRequest request) throws Error
    {
        log.error("Connector:triggerData is not implemented in this connector");
        ArrayList<FlowError> errors = new ArrayList<FlowError>();
        errors.add(new FlowError("NotImplemented", "Connector:triggerData is not implemented in this connector", null, null));
        TriggerResult result = new TriggerResult(request, 0, 0, errors );
        return result;
    }

    /**
     * Trigger Stop request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     * Used when trigger type is LISTENING.
     * Stop listening to streamed data for a trigger.
     *
     * @param request The trigger listen request
     * @returns {Object} promise with trigger_result or error.
     * @api public
     */
    @Override
    public boolean triggerStop( TriggerStopRequest request )
    {
        log.error("Connector:triggerStop is not implemented in this connector");
        return false;
    }

    /**
     * Execute Action request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     * This function is used when executing an action from target connector
     *
     * @param request The execute action poll request
     * @returns {Object} promise with action_result or error.
     * @api public
     */
    @Override
    public ActionResult executeAction( ExecuteActionRequest request )
    {
        log.debug("Processing Execute Action Request: "+request.getAction());
        ActionResult result;
        PanamaRegistration.Action action = null;
        for (PanamaRegistration.Action paction : _registration.getActions())
        {
            if (paction.getName().equals(request.getAction()))
            {
                action = request.getFlow().getActions().get(0).getOperation();
                break;
            }
        }

        if (action != null)
        {
            // pick parameters out of data if not already split out
            if ((action.getParameters() != null) && (request.getParameters() == null))
            {
                Map<String, Object> parameters = new HashMap<String, Object>();
                for (PanamaRegistration.Field param : action.getParameters())
                {
                    String key = param.getSystemName();
                    parameters.put(key, request.getData().get(key));
                }
                request.setParameters(parameters);
            }

            switch (request.getAction()) {

                case "Getting_Incidents":
                    result = ActionGetting_Incidents(request, action);
                    break;


                default:
                    ArrayList<FlowError> errors = new ArrayList<FlowError>();
                    errors.add(new FlowError("UnknownAction", "Unknown action excution requested: "+request.getAction(), null, null));
                    result = new ActionResult(request, false, errors, null);
            }
        }
        else
        {
            ArrayList<FlowError> errors = new ArrayList<FlowError>();
            errors.add(new FlowError("UnknownAction", "Unknown action excution requested: "+request.getAction(), null, null));
            result = new ActionResult(request, false, errors, null);
        }

        return result;
    }

    /**
     * Do Lookup request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     * Used for providing the result for fields requiring lookup
     *
     * @param request The do lookup request
     * @returns {Object} promise with lookup_result or error.
     * @api public
     */
    @Override
    public LookupResult doLookup( DoLookupRequest request )
    {
        log.debug("Processing Lookup Request: "+request.getLookup());
        LookupResult result;
        PanamaRegistration.Lookup lookup = null;
        for (PanamaRegistration.Lookup plookup : _registration.getLookups())
        {
            if (plookup.getName().equals(request.getLookup()))
            {
                lookup = request.getLookupSchema();
                break;
            }
        }

        if (lookup != null)
        {
            switch (request.getLookup()) {


                default:
                    ArrayList<FlowError> errors = new ArrayList<FlowError>();
                    errors.add(new FlowError("UnknownLookup", "Unknown lookup requested: "+request.getLookup(), null, null));
                    result = new LookupResult(request, false, errors, null);
            }
        }
        else
        {
            ArrayList<FlowError> errors = new ArrayList<FlowError>();
            errors.add(new FlowError("UnknownLookup", "Unknown lookup requested: "+request.getLookup(), null, null));
            result = new LookupResult(request, false, errors, null);
        }

        return result;
    }

    /**
     * Validate Connection request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     *
     * @param request The valicate connection request
     * @returns {Object} promise with lookup_result or error.
     * @api public
     */
    @Override
    public ValidationResult validateConnection( ValidateConnectionRequest request )
    {
        log.debug("Processing Validation Request.");
        boolean successful = false;
        ArrayList<FlowError> errors = new ArrayList<FlowError>();
        try
        {
            // TODO: Add here logic to verify user credentials
            successful = true;
        }
        catch ( Exception e )
        {
            errors.add(new FlowError("ConnectionValidationError", e.getMessage(), null, null));
            successful = false;
        }
        ValidationResult result = new ValidationResult(successful, errors);
        return result;
    }

    /**
     * Generate Custom Schema request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     *
     * @param {Object} request The request object.
     * @returns {Object} promise with validation_result or error.
     * @api public
     */
    public CustomSchemaResult generateCustomSchema( CustomSchemaRequest request) throws Error
    {
        log.error("Connector:generateCustomSchema is not implemented in this connector");
        ArrayList<FlowError> errors = new ArrayList<FlowError>();
        errors.add(new FlowError("NotImplemented", "Connector:generateCustomSchema is not implemented in this connector", null, null));
        CustomSchemaResult result = new CustomSchemaResult(false, errors );
        return result;
    }

    /**
     * Custom Call request.
     *
     * This function must be overridden by subclasses.  In abstract form, it always
     * throws an exception.
     *
     * @param request The custom call request
     * @returns {Object} promise with action_result or error.
     * @api public
     */
    public CustomCallResult customCall( CustomCallRequest request ) throws Error
    {
        log.error("customCall is not implemented in this connector");
        ArrayList<FlowError> errors = new ArrayList<FlowError>();
        errors.add(new FlowError("NotImplemented", "customCall is not implemented in this connector", null, null));
        CustomCallResult result = new CustomCallResult(request, false, errors, null );
        return result;
    }



    /**
     * Action processor for Getting_Incidents trigger
     */
    private ActionResult ActionGetting_Incidents(ExecuteActionRequest request, PanamaRegistration.Action action)
    {
        boolean successful = false;

        // Action input parameters (none here)
        Map<String, Object> data = request.getData();

        // Output
        IncidentResult incidentResult = new IncidentResult();
        HashMap<String, Object> output_data = new HashMap<String, Object>();
        ArrayList<FlowError> errors = new ArrayList<FlowError>();

        ARServerUser server;

        // Setting connection information
        String serverHost = "xxx.xxx.xxx.xxx";
        String userName = "xxxxxxxxxxxxxx";
        String userPassword = "xxxxxxxxx";
        Integer serverPort = 3500;

        log.warn("After getting parameters");

        // Trying to connect to the AR Server
        log.warn("Before new ARServerUser()");
        server = new ARServerUser();
        log.warn("After new ARServerUser()");

        server.setServer(serverHost);
        server.setPort(serverPort);
        server.setUser(userName);
        server.setPassword(userPassword);
        server.setTimeoutNormal(3600); // timeout in seconds
        server.setTimeoutLong(3600);
        server.setTimeoutXLong(3600);

        try
        {
            // Testing Server connection
            log.warn("Testing connection");

            int[] ar_info = {
                    Constants.AR_SERVER_INFO_VERSION,
                    Constants.AR_SERVER_INFO_SERVER_NAME
            };

            server.getServerInfo(ar_info);

            log.warn("Connection successful");

            // Getting the incident and building a list.
            List<Incident> incidentList = new ArrayList<>();
            String formName = "HPD:Help Desk";
            String qualification = "1=1";
            List <Field> fieldList = server.getListFieldObjects(formName);
            QualifierInfo qualifierInfo = server.parseQualification(qualification, fieldList, null, Constants.AR_QUALCONTEXT_DEFAULT);

            // Field list:
            int[] dataFields = {1, 1000000000, 179, 1000000161};
            OutputInteger numberOfMatches = new OutputInteger();

            // Sort Order
            List<SortInfo> sortOrder = new ArrayList<SortInfo>();
            sortOrder.add(new SortInfo(1, Constants.AR_SORT_DESCENDING));

            // Fetching data
            List<Entry> entryList = server.getListEntryObjects(formName, qualifierInfo, 0, Constants.AR_NO_MAX_LIST_RETRIEVE, sortOrder, dataFields, true, numberOfMatches);

            if (numberOfMatches.intValue() > 0) {
                for (int i = 0; i < entryList.size(); i++) {
                    Incident incident = new Incident();
                    Entry entry = entryList.get(i);

                    incident.setGUID(entry.get(Constants.AR_RESERV_GUID).toString());
                    incident.setIncidentNumber(entry.get(1000000161).toString());
                    incident.setSummary(entry.get(1000000000).toString());
                    incidentList.add(incident);
                }
            }

            // Creating the object sent back by the connector
            incidentResult.setIncidentList(incidentList);
            incidentResult.setIncidentCount();
            successful = true;
        }
        catch ( Exception e )
        {
            errors.add(new FlowError("ServiceError", e.getMessage(), null, null));
            successful = false;
            log.error("Connection failed");
        }

        // Preparing the output parameter, as a string.
        // We use object mapper to stringify the incidentResult object.
        try {
//            log.warn("Object stringified: "+mapper.writeValueAsString(incidentResult));
            output_data.put(OUTPUT_GET_INCIDENTS, mapper.writeValueAsString(incidentResult));
        } catch (JsonProcessingException e) {
            output_data.put("ERROR", "JsonProcessingException");
			output_data.put(OUTPUT_GET_INCIDENTS, "JsonProcessingException");
            log.error("LMA ERROR stringifying the incident result");
            log.error(e.getMessage());
        }

        // Send output_data in ActionResult object
        ActionResult result = new ActionResult(request, successful, errors, output_data);

        return result;
    }



}
