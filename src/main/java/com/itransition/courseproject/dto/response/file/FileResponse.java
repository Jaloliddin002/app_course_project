package com.itransition.courseproject.dto.response.file;

import com.itransition.courseproject.dto.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse implements Response {
    List<String> path;
}
