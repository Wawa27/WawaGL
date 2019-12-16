#version 450

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 model;

out vec3 aPosition;
out vec3 oColor;

void main() {
    vec4 world_position = vec4(position, 1.0);
    gl_Position = projection * view * world_position;
    aPosition = position;
    oColor = vec3(.15, .15, .6);
    if (world_position.y > 0 && world_position.y < 13) {
        oColor = vec3(0, 1, 0);
    } else if (world_position.y >= 13) {
        oColor = vec3(1, 1, 1);
    }
}
