import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunIdGenerator
{
    @Autowired
    private CommonUtil commonUtil;

    private static final ComponentLogger logger = ComponentLoggerFactory.getLogger(RunIdGenerator.class);

    public String generateRunId(Context context, String fileName)
    {
        String runId = getMD5Hash(context, fileName);
        return runId;
    }

    public String getMD5Hash(Context context, String fileName)
    {
        logger.trace(() -> ">> getMD5Hash()");

        String generatedKey = getHashKey(context, fileName);
        String runID = (StringUtils.isEmpty(generatedKey)) ? null : commonUtil.createMD5Hash(generatedKey);

        logger.trace(() -> "<< getMD5Hash() with run id " + runID);

        return runID;
    }

    /**
     * This API create Hash key depending on fileName and timeStamp
     * @param context
     * @param fileName
     * @return
     */
    public String getHashKey(Context context, String fileName)
    {
        logger.trace(">> getHashKey()");
        String hashKey = null;
        if (!StringUtils.isEmpty(fileName))
        {
            hashKey = StringUtils.isEmpty(fileName) ? "" : (fileName).trim();
            hashKey = StringUtils.isEmpty(hashKey) ? LocalDateTime.now().toString() :
                    hashKey + "|" + LocalDateTime.now().toString();
        }

        logger.trace("<< getHashKey()" + hashKey);
        return hashKey;
    }
}
