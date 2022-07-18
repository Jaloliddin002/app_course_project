package com.itransition.courseproject.util;

import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.entity.collection.Field;
import com.itransition.courseproject.model.entity.item.FieldValue;
import com.itransition.courseproject.model.enums.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public final class CSVUtil {

    public static List<String[]> ofCollection(String lang, Collection collection, List<Field> fieldList, List<FieldValue> fieldValueList) {

        List<String[]> data = new ArrayList<>();
        data.addAll(collection.toCSVString(lang));
        data.addAll(collectionItemFieldNameLists(fieldList));
        data.addAll(valuesCollectionItemFields(fieldValueList, fieldList.size(), lang));
        return data;
    }

    public static List<String[]> collectionItemFieldNameLists(List<Field> fieldList) {
        int length = fieldList.size();
        String[] head = new String[length + 1];
        head[0] = "Id";
        for (int i = 0; i < length; i++) {
            head[i + 1] = fieldList.get(i).getName();
        }
        return Collections.singletonList(head);
    }

    public static List<String[]> valuesCollectionItemFields(List<FieldValue> fieldValueList, int fieldCount, String lang) {
        final List<String[]> fieldData = new ArrayList<>();
        String[] itemContents = new String[fieldCount + 1]; // 4 + 4 fieldCount = 5
        int columnCount = 0, i = 0;
        while (i < fieldValueList.size()) {
            itemContents[columnCount++] = String.valueOf(fieldValueList.get(i).getItem().getId());
            for (int j = 0; j < fieldCount; j++) {
                if (j == 1) {
                    itemContents[columnCount++] = "[" + fieldValueList
                            .get(i)
                            .getItem()
                            .getTags()
                            .stream()
                            .map(tag -> lang.equals(Language.ENG.languageENG) ? tag.getNameENG() : tag.getNameRUS())
                            .collect(Collectors.joining(" , "))
                            + "]";
                } else {
                    itemContents[columnCount++] = fieldValueList.get(i++).getValue();
                }
            }
            fieldData.add(itemContents);
            columnCount = 0;
            itemContents = new String[fieldCount + 1];
        }
        return fieldData;
    }
}
