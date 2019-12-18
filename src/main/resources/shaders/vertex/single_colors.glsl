#version 450

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;
layout(location = 5) in vec3 colors;

out vec2 oTexCoord;
out flat vec3 oNormal;
out vec3 oPosition;
out flat vec3 oColors;

out vec4 gl_Position;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

void main() {
    oPosition = vec3(view * model * vec4(position, 1.0));
    oNormal = normalize(transpose(inverse(mat3(view * model))) * normal);
    oTexCoord = texCoord;
    oColors = colors;

    gl_Position = projection * view * model * vec4(position, 1.0);
}
