#version 450

const int MAX_INSTANCE_COUNT = 256;

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

out vec2 oTexCoord;
out vec3 oNormal;
out vec3 oPosition;
out flat int oInstanceID;

out vec4 gl_Position;

layout(std140, binding = 0) uniform ViewProjection {
    mat4 view;
    mat4 projection;
};

uniform mat4 models[MAX_INSTANCE_COUNT];

void main() {
    oPosition = vec3(view * models[gl_InstanceID] * vec4(position, 1.0));
    oNormal = normalize(transpose(inverse(mat3(view * models[gl_InstanceID]))) * normal);
    oTexCoord = texCoord;
    oInstanceID = gl_InstanceID;

    gl_Position = projection * view * models[gl_InstanceID] * vec4(position, 1.0);
}
